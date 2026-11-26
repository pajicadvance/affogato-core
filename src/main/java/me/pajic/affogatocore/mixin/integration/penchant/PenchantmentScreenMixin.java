package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.client.gui.screen.PenchantmentScreen;
import archives.tater.penchant.client.gui.widget.EnchantmentSlotWidget;
import archives.tater.penchant.menu.PenchantmentMenu;
import archives.tater.penchant.util.PenchantmentHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.integration.PenchantLevelUpSlotWidget;
import me.pajic.affogatocore.integration.PenchantLevelUps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@IfModLoaded("penchant")
@Mixin(PenchantmentScreen.class)
public class PenchantmentScreenMixin {

    @WrapOperation(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Larchives/tater/penchant/client/gui/screen/PenchantmentScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;"
            )
    )
    private GuiEventListener useLevelUpWidget(PenchantmentScreen instance, GuiEventListener widget, Operation<GuiEventListener> original) {
        PenchantmentMenu menu = instance.getMenu();
        if (PenchantLevelUps.isEnabled() && !menu.isDisenchanting() && widget instanceof EnchantmentSlotWidget slotWidget) {
            Holder<Enchantment> enchantment = ((EnchantmentSlotWidgetAccessor) slotWidget).ac$getEnchantment();
            ItemStack stack = menu.getEnchantingStack();
            int pendingLevels = PenchantLevelUps.getPendingLevels(stack, enchantment);
            if (pendingLevels > 0) {
                boolean hasEnoughXp = Objects.requireNonNull(Minecraft.getInstance().player).hasInfiniteMaterials()
                        || PenchantmentHelper.getXpLevelCost(enchantment) <= menu.getPlayerXp();
                return original.call(instance, new PenchantLevelUpSlotWidget(
                        slotWidget.getX(),
                        slotWidget.getY(),
                        enchantment,
                        stack.getEnchantments().getLevel(enchantment),
                        pendingLevels,
                        hasEnoughXp
                ));
            }
        }
        return original.call(instance, widget);
    }
}
