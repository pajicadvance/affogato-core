package me.pajic.affogatocore.mixin.worldgen;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MineshaftStructure.Type.class)
public enum MineshaftStructureTypeMixin {

    AFFOGATOCORE_DEEPSLATE("affogatocore_deepslate", Blocks.SPRUCE_LOG, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_FENCE);

    @Shadow MineshaftStructureTypeMixin(String name, Block wood, Block plank, Block fence) {}
}
