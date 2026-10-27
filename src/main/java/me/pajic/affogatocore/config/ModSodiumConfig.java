package me.pajic.affogatocore.config;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.AffogatoCoreClient;
import me.pajic.affogatocore.iris.IrisHelper;
import me.pajic.affogatocore.iris.ShaderPresetSetup;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.caffeinemc.mods.sodium.api.config.structure.OptionBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.util.Locale;

public class ModSodiumConfig implements ConfigEntryPoint {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        builder.registerOwnModOptions()
                .setName("Affogato Core")
                .setVersion(FabricLoader.getInstance().getModContainer(AffogatoCore.MOD_ID).get().getMetadata().getVersion().getFriendlyString().substring(0, 4))
                .setColorTheme(builder.createColorTheme().setBaseThemeRGB(0xD0C5C0))
                .setNonTintedIcon(AffogatoCore.id("textures/config_icon.png"))
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("config.affogatocore.settings"))
                        .addOption(setupShaderPresetOption(builder))
                );
    }

    private static OptionBuilder setupShaderPresetOption(ConfigBuilder builder) {
        return ShaderPresetSetup.allPresetsCreated ?
                builder.createIntegerOption(AffogatoCore.id("shader_preset"))
                    .setName(Component.translatable("config.affogatocore.shaderPreset"))
                    .setTooltip(Component.translatable("config.affogatocore.shaderPreset.tooltip"))
                    .setRange(-1, ShaderPresetSetup.presets.size() - 1, 1)
                    .setDefaultValue(-1)
                    .setValueFormatter(i -> {
                        if (i == -1) return Component.translatable("config.affogatocore.shaderOff");
                        return Component.translatable("config.affogatocore.shaderPreset." + ShaderPresetSetup.presets.get(i).toLowerCase(Locale.ROOT).replace(' ', '_'));
                    })
                    .setBinding(ModSodiumConfig::saveShaderPreset, ModSodiumConfig::getShaderPreset)
                    .setStorageHandler(AffogatoCoreClient.CONFIG::save) :
                builder.createBooleanOption(AffogatoCore.id("shader_preset_unavailable"))
                    .setName(Component.translatable("config.affogatocore.shaderPreset"))
                    .setTooltip(Component.translatable("config.affogatocore.shaderPresetUnavailable.tooltip"))
                    .setEnabled(false)
                    .setDefaultValue(false)
                    .setBinding(_ -> {}, () -> false)
                    .setStorageHandler(() -> {});
    }

    private static int getShaderPreset() {
        return IrisHelper.updatePreset(ShaderPresetSetup.presets.indexOf(AffogatoCoreClient.CONFIG.shaderPreset.get()));
    }

    private static void saveShaderPreset(int preset) {
        if (preset == -1) AffogatoCoreClient.CONFIG.shaderPreset.accept("");
        else AffogatoCoreClient.CONFIG.shaderPreset.validateAndSet(ShaderPresetSetup.presets.get(preset));
        IrisHelper.applyPreset(preset);
    }
}
