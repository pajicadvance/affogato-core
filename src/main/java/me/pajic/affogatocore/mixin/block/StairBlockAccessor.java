package me.pajic.affogatocore.mixin.block;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StairBlock.class)
public interface StairBlockAccessor {

    @Invoker("<init>")
    static StairBlock ac$create(BlockState baseState, BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }
}
