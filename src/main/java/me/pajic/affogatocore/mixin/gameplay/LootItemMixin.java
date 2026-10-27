package me.pajic.affogatocore.mixin.gameplay;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.LootEntryReplacement;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
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
    private <T> T swapLootItem(T value, @Local(argsOnly = true, name = "context") final LootContext context) {
        Registry<Item> registry = context.getLevel().registryAccess().lookupOrThrow(Registries.ITEM);
        Identifier stackItemId = registry.getKey(((ItemStack) value).getItem());
        if (stackItemId != null) {
            LootEntryReplacement replacement = AffogatoCore.CONFIG.loot.lootEntryReplacements.get().get(stackItemId.toString());
            if (replacement != null) {
                Item replacementItem = registry.getValue(Identifier.parse(replacement.replacementItem.get()));
                if (replacementItem != null) {
                    ItemStack replacementStack = new ItemStack(replacementItem);
                    replacementStack.setCount(replacement.replacementCount.get());
                    return (T) replacementStack;
                }
            }
        }
        return value;
    }
}
