package me.pajic.affogatocore.mixin.integration.variantsandventures;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.config.VariantsAndVenturesConfig;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.CompatFlags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded("variantsandventures")
@Mixin(value = VariantsAndVenturesConfig.class, remap = false)
public class VariantsAndVenturesConfigMixin {

    @Unique private static boolean ac$configuredEnableGelid = true;

    @Inject(
            method = "load",
            at = @At("TAIL")
    )
    private static void disableGelidWithEmbersAndIcicles(CallbackInfo ci) {
        if (!CompatFlags.EMBERS_AND_ICICLES_LOADED) return;
        VariantsAndVenturesConfig config = VariantsAndVentures.getConfig();
        ac$configuredEnableGelid = config.enableGelid;
        config.enableGelid = false;
    }

    @WrapMethod(method = "save")
    private static void keepConfiguredGelid(Operation<Void> original) {
        if (!CompatFlags.EMBERS_AND_ICICLES_LOADED) original.call();
        else {
            VariantsAndVenturesConfig config = VariantsAndVentures.getConfig();
            config.enableGelid = ac$configuredEnableGelid;
            original.call();
            config.enableGelid = false;
        }
    }
}
