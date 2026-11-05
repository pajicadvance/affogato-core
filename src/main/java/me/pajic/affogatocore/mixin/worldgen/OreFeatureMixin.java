package me.pajic.affogatocore.mixin.worldgen;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.config.ModConfig;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.AbstractOreFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OreFeature.class)
public class OreFeatureMixin {

    @Unique private static final ThreadLocal<Integer> AC$SCALED_SIZE = new ThreadLocal<>();

    @WrapMethod(method = "place")
    private boolean scaleOreSize(
            WorldGenLevel level,
            ChunkGenerator chunkGenerator,
            RandomSource random,
            BlockPos origin,
            Operation<Boolean> original
    ) {
        AbstractOreFeature self = (AbstractOreFeature) (Object) this;
        if (level.getLevel().dimension() != Level.OVERWORLD || self.targetStates().stream().noneMatch(target -> target.state().is(ConventionalBlockTags.ORES))) {
            return original.call(level, chunkGenerator, random, origin);
        }
        AC$SCALED_SIZE.set(Math.round(self.size() * ac$getSizeMult(origin.getY())));
        boolean result = original.call(level, chunkGenerator, random, origin);
        AC$SCALED_SIZE.remove();
        return result;
    }

    @ModifyExpressionValue(
            method = {"place", "doPlace"},
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/level/levelgen/feature/OreFeature;size:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int useScaledSize(int original) {
        Integer scaledSize = AC$SCALED_SIZE.get();
        return scaledSize != null ? scaledSize : original;
    }

    @Unique
    private static float ac$getSizeMult(int y) {
        ModConfig.Worldgen config = AffogatoCore.CONFIG.worldgen;
        int maxY = config.oreSizeMultMaxY.get();
        int midY = config.oreSizeMultMidY.get();
        int minY = config.oreSizeMultMinY.get();
        float maxMult = config.oreSizeMaxMult.get();
        float minMult = config.oreSizeMinMult.get();
        if (y >= maxY) return minMult;
        if (y <= minY) return maxMult;
        if (y >= midY) return Mth.lerp((float) (y - midY) / (maxY - midY), 1.0F, minMult);
        return Mth.lerp((float) (midY - y) / (midY - minY), 1.0F, maxMult);
    }
}
