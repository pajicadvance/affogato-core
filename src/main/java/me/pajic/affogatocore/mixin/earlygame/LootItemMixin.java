package me.pajic.affogatocore.mixin.earlygame;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LootItem.class)
public abstract class LootItemMixin {

    @SuppressWarnings("unchecked")
	@ModifyArg(
            method = "createItemStack",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            )
    )
    private <T> T swapLootItem(T stack) {
        if (AffogatoCore.CONFIG.packs.earlyGameExtension.get()) {
            ItemStack original = (ItemStack) stack;
            if (original.is(Items.WOODEN_AXE)) return (T) new ItemStack(ModItems.FLINT_AXE);
            if (original.is(Items.WOODEN_HOE)) return (T) new ItemStack(ModItems.FLINT_HOE);
            if (original.is(Items.WOODEN_PICKAXE)) return (T) new ItemStack(ModItems.FLINT_PICKAXE);
            if (original.is(Items.WOODEN_SHOVEL)) return (T) new ItemStack(ModItems.FLINT_SHOVEL);
            if (original.is(Items.WOODEN_SWORD)) return (T) new ItemStack(ModItems.FLINT_SWORD);
            if (original.is(Items.WOODEN_SPEAR)) return (T) new ItemStack(ModItems.FLINT_SPEAR);
        }
        return stack;
    }
}
