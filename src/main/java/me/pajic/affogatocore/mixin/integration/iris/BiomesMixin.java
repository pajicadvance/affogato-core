package me.pajic.affogatocore.mixin.integration.iris;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded("iris")
@Mixin(Biomes.class)
public class BiomesMixin {

    @Shadow
    private static ResourceKey<Biome> register(String name) {
        throw new AssertionError();
    }

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void registerIceCavesForShaders(CallbackInfo ci) {
        register("ice_caves");
    }
}
