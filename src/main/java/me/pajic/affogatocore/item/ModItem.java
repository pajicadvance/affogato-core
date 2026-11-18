package me.pajic.affogatocore.item;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class ModItem extends Item {

    private final boolean enabled;

    public ModItem(Properties properties, String id, boolean enabled) {
        super(properties.setId(ResourceKey.create(Registries.ITEM, AffogatoCore.id(id))));
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled(@NotNull FeatureFlagSet featureFlagSet) {
        return enabled;
    }
}
