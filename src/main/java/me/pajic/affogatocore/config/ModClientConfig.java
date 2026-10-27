package me.pajic.affogatocore.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.util.AllowableStrings;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.iris.ShaderPresetSetup;

@Version(version = 1)
public class ModClientConfig extends Config {

    public ModClientConfig() {
        super(AffogatoCore.id("client_config"));
    }

    public ValidatedString shaderPreset = new ValidatedString("", new AllowableStrings(
            ShaderPresetSetup.presets::contains, () -> ShaderPresetSetup.presets)
    );
    public ValidatedString targetShaderString = new ValidatedString("");
    public ValidatedBoolean disableLBGLayers = new ValidatedBoolean(false);
    public ValidatedBoolean deleteModdedSplashTexts = new ValidatedBoolean(false);
    public ValidatedBoolean langOverrides = new ValidatedBoolean(false);
}
