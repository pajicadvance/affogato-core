package me.pajic.affogatocore.item;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ModBlockItem extends BlockItem {

    private final boolean enabled;

    public ModBlockItem(Block block, Properties properties, boolean enabled) {
        super(block, properties);
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled(@NotNull FeatureFlagSet featureFlagSet) {
        return enabled;
    }
}
