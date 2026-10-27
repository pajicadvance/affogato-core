package me.pajic.affogatocore.mixin.earlygame;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.VillagerTrade;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerTrade.class)
public class VillagerTradeMixin {

    @ModifyExpressionValue(
            method = "getOffer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStackTemplate;create()Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack swapTradeItem(ItemStack original) {
        if (AffogatoCore.CONFIG.packs.earlyGameExtension.get()) {
            if (original.is(Items.WOODEN_AXE)) return new ItemStack(ModItems.FLINT_AXE);
            if (original.is(Items.WOODEN_HOE)) return new ItemStack(ModItems.FLINT_HOE);
            if (original.is(Items.WOODEN_PICKAXE)) return new ItemStack(ModItems.FLINT_PICKAXE);
            if (original.is(Items.WOODEN_SHOVEL)) return new ItemStack(ModItems.FLINT_SHOVEL);
            if (original.is(Items.WOODEN_SWORD)) return new ItemStack(ModItems.FLINT_SWORD);
            if (original.is(Items.WOODEN_SPEAR)) return new ItemStack(ModItems.FLINT_SPEAR);
            if (original.is(Items.STONE_AXE)) return new ItemStack(Items.COPPER_AXE);
            if (original.is(Items.STONE_HOE)) return new ItemStack(Items.COPPER_HOE);
            if (original.is(Items.STONE_PICKAXE)) return new ItemStack(Items.COPPER_PICKAXE);
            if (original.is(Items.STONE_SHOVEL)) return new ItemStack(Items.COPPER_SHOVEL);
            if (original.is(Items.STONE_SWORD)) return new ItemStack(Items.COPPER_SWORD);
            if (original.is(Items.STONE_SPEAR)) return new ItemStack(Items.COPPER_SPEAR);
        }
        return original;
    }
}
