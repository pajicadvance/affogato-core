package me.pajic.affogatocore.mixin.gameplay;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Set;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin {

    @Unique private static final Set<ResourceKey<ContextIntProvider>> DISALLOWED_FUELS = Set.of(
            ContextIntProviders.COOKING_TIME_BLAZE_ROD,
            ContextIntProviders.COOKING_TIME_DRIED_KELP_BLOCK,
            ContextIntProviders.COOKING_TIME_WOOL,
            ContextIntProviders.COOKING_TIME_WOOL_CARPETS,
            ContextIntProviders.COOKING_TIME_WOOL_SLABS
    );

    @WrapMethod(method = "cookingFuel")
    private Item.Properties removeFuels(ResourceKey<ContextIntProvider> burnTime, Operation<Item.Properties> original) {
        if (AffogatoCore.CONFIG.packs.antiFarmPack.get() && DISALLOWED_FUELS.stream().anyMatch(
                key -> key.identifier().equals(burnTime.identifier())
        )) return (Item.Properties) (Object) this;
        return original.call(burnTime);
    }
}
