package me.pajic.affogatocore.mixin.hunger;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {

    @WrapMethod(method = "releaseUsing")
    private boolean throwWeaponExhaustion(ItemStack itemStack, Level level, LivingEntity entity, int remainingTime, Operation<Boolean> original) {
        if (entity instanceof Player player && !level.isClientSide() && itemStack.is(ItemTags.AXES)) {
            player.causeFoodExhaustion(AffogatoCore.CONFIG.exhaustionValues.throwWeapon.get());
        }
        return original.call(itemStack, level, entity, remainingTime);
    }
}
