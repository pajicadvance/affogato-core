package me.pajic.affogatocore.iris;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectObjectImmutablePair;
import me.pajic.affogatocore.AffogatoCoreClient;
import me.pajic.affogatocore.util.SHA256;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// sets up shader presets when shader config files are provided in a correct format inside the
// `shaderpacks/affogato_presets` directory and a shader file is present whose name contains
// the target shader string configured in the client config
public class ShaderPresetSetup {

    private static final Path SHADER_DIR = FabricLoader.getInstance().getGameDir().resolve("shaderpacks");
    private static final Path SHADER_PRESETS_DIR = SHADER_DIR.resolve("affogato_presets");
    private static final Path SHADER_HASH_PATH = SHADER_DIR.resolve("affogato_shader_hash");
    public static List<String> presets = new ArrayList<>();
    public static boolean allPresetsCreated = false;
    private static final Gson GSON = new GsonBuilder().create();

    @SuppressWarnings("DataFlowIssue") // listFiles() is always called on files that are guaranteed to be directories
    public static void init() {
        File shaderDir = SHADER_DIR.toFile();
        File shaderPresetsDir = SHADER_PRESETS_DIR.toFile();
        List<ObjectObjectImmutablePair<String, String>> savedHash = new ArrayList<>();
        if (shaderDir.exists() && shaderPresetsDir.exists() && shaderDir.isDirectory() && shaderPresetsDir.isDirectory()) {
            // check if the preset files are correctly named and add them
            try (Stream<Path> fileStream = Files.walk(SHADER_PRESETS_DIR, 1)) {
                fileStream.filter(path -> !path.toFile().getName().equals("affogato_presets"))
                        .map(path -> path.toFile().getName().split("_", 2))
                        .forEach(split -> {
                    try {
                        presets.add(Integer.parseInt(split[0]), split[1].split("\\.")[0]);
                    } catch (NumberFormatException e) {
                        AffogatoCoreClient.LOGGER.error("Invalid preset file name format, skipping", e);
                    }
                });
            } catch (IOException e) {
                AffogatoCoreClient.LOGGER.error("Can't access file, did not create shader presets", e);
            }
            // check if presets were generated previously and if yes get the saved hash
            try {
                savedHash = GSON.fromJson(new FileReader(SHADER_HASH_PATH.toFile()), new TypeToken<List<ObjectObjectImmutablePair<String, String>>>() {}.getType());
                AffogatoCoreClient.LOGGER.info("Found existing presets");
            } catch (FileNotFoundException e) {
                AffogatoCoreClient.LOGGER.info("No existing presets found");
            }
            // scan shader directory for shader files and compare hashes
            File targetShader = null;
            try (Stream<Path> fileStream = Files.walk(SHADER_DIR, 1)) {
                Set<File> files = fileStream.map(Path::toFile).collect(Collectors.toUnmodifiableSet());
                for (File shader : files) {
                    String target = AffogatoCoreClient.CONFIG.targetShaderString.get();
                    String name = shader.getName();
                    if (!target.isBlank() && name.contains(target) && !name.endsWith(".txt")) {
                        List<ObjectObjectImmutablePair<String, String>> hash = new ArrayList<>();
                        if (shader.isDirectory()) {
                            try (Stream<Path> fileStream1 = Files.walk(shader.toPath())) {
                                fileStream1.forEach(path -> {
                                    try {
                                        File file = path.toFile();
                                        String fileName = file.getName();
                                        if (!fileName.startsWith(".") && !file.isDirectory()) {
                                            hash.add(ObjectObjectImmutablePair.of(SHA256.calculateSHA256(file), fileName));
                                        }
                                    } catch (IOException | NoSuchAlgorithmException e) {
                                        AffogatoCoreClient.LOGGER.error("Failed to process file in shader directory", e);
                                    }
                                });
                            } catch (IOException e) {
                                AffogatoCoreClient.LOGGER.error("Failed to process shader directory", e);
                            }
                            hash.sort(Comparator.comparing((Pair<String, String> p) -> p.right()).thenComparing(p -> p.left()));
                        } else if (shader.isFile() && name.endsWith(".zip")) {
                            hash.add(ObjectObjectImmutablePair.of(SHA256.calculateSHA256(shader), name));
                        }
                        if (savedHash.isEmpty() || !savedHash.equals(hash)) {
                            savedHash = hash;
                            targetShader = shader;
                        }
                    }
                }
            } catch (IOException | NoSuchAlgorithmException e) {
                AffogatoCoreClient.LOGGER.error("Failed to create shader presets", e);
            }
            // if no existing presets are found or hashes don't match, update shader presets
            if (!savedHash.isEmpty() && targetShader != null) {
                AffogatoCoreClient.LOGGER.info("Updating shader presets");
                for (int i = 0; i < presets.size(); i++) {
                    String preset = presets.get(i);
                    File shaderCopy = SHADER_DIR.resolve("Affogato_" + preset + ".zip").toFile();
                    try {
                        if (shaderCopy.exists() && shaderCopy.isDirectory()) FileUtils.deleteDirectory(shaderCopy);
                        if (targetShader.isDirectory()) FileUtils.copyDirectory(targetShader, shaderCopy);
                        else FileUtils.copyFile(targetShader, shaderCopy);
                        FileUtils.copyFile(
                                SHADER_PRESETS_DIR.resolve(i + "_" + preset + ".txt").toFile(),
                                SHADER_DIR.resolve("Affogato_" + preset + ".zip.txt").toFile()
                        );
                        AffogatoCoreClient.LOGGER.info("Created shader preset {}", preset);
                    } catch (IOException e) {
                        AffogatoCoreClient.LOGGER.error("Couldn't copy preset {}, skipping", preset, e);
                    }
                }
            }
            // check if all presets got created
            if (!presets.isEmpty() && presets.stream().allMatch(preset ->
                    Arrays.stream(shaderDir.listFiles()).anyMatch(file ->
                            file.getName().equals("Affogato_" + preset + ".zip.txt")
                    )
            )) {
                // let the Sodium config option know that shader presets can be used
                AffogatoCoreClient.LOGGER.info("Shader presets ready");
                allPresetsCreated = true;
                // write shader hash info to file
                try (FileWriter writer = new FileWriter(SHADER_HASH_PATH.toFile())) {
                    GSON.toJson(savedHash, writer);
                    writer.flush();
                } catch (IOException e) {
                    AffogatoCoreClient.LOGGER.error("Couldn't write version info (how), skipping", e);
                }
            }
        }
    }
}
