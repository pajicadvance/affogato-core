package me.pajic.affogatocore.iris;

import me.pajic.affogatocore.AffogatoCoreClient;
import net.irisshaders.iris.Iris;

import java.io.IOException;
import java.util.Optional;

public class IrisHelper {

    public static void applyPreset(int preset) {
        if (Iris.getIrisConfig() != null) {
            if (preset == -1) Iris.getIrisConfig().setShadersEnabled(false);
            else {
                Iris.getIrisConfig().setShaderPackName("Affogato_" + ShaderPresetSetup.presets.get(preset) + ".zip");
                Iris.getIrisConfig().setShadersEnabled(true);
            }
            try {
                Iris.getIrisConfig().save();
                Iris.reload();
            } catch (IOException e) {
                AffogatoCoreClient.LOGGER.error("Error while reloading Shaders for Iris!", e);
            }
        }
    }

    public static int updatePreset(int preset) {
        if (Iris.getIrisConfig() != null) {
            if (preset != -1) {
                Optional<String> opt = Iris.getIrisConfig().getShaderPackName();
                if (opt.isEmpty() || !opt.get().startsWith("Affogato_")) return -1;
                return preset;
            }
        }
        return -1;
    }
}
