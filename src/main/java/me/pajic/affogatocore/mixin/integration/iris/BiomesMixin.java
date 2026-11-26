package me.pajic.affogatocore.mixin.integration.iris;

import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.irisshaders.iris.uniforms.BiomeUniforms;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@IfModLoaded("iris")
@Mixin(Biomes.class)
public class BiomesMixin {

    @Inject(
            method = "<clinit>",
            at = @At("TAIL")
    )
    private static void registerIceCavesForShaders(CallbackInfo ci) {
        Object2IntMap<ResourceKey<Biome>> biomeMap = BiomeUniforms.getBiomeMap();
        ResourceKey<Biome> iceCaves = ResourceKey.create(Registries.BIOME, Identifier.withDefaultNamespace("ice_caves"));
        if (biomeMap.containsKey(iceCaves)) return;
        int nextId = biomeMap.values().intStream().max().orElse(-1) + 1;
        biomeMap.put(iceCaves, nextId);
    }
}
