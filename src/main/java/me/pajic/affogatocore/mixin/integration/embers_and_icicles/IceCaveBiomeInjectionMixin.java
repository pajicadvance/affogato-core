package me.pajic.affogatocore.mixin.integration.embers_and_icicles;

import cc.cassian.effects.worldgen.IceCaveBiomeInjection;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.CompatFlags;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("embers_and_icicles")
@Mixin(value = IceCaveBiomeInjection.class, remap = false)
public class IceCaveBiomeInjectionMixin {

    @WrapMethod(method = "shouldInject")
    private static boolean disableWithClifftree(Operation<Boolean> original) {
        return !CompatFlags.CLIFFTREE_LOADED && original.call();
    }
}
