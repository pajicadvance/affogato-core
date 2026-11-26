package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.PenchantClient;
import archives.tater.penchant.client.FontUtils;
import archives.tater.penchant.component.EnchantmentProgress;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import me.pajic.affogatocore.integration.PenchantLevelUps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;

@IfModLoaded("penchant")
@Mixin(PenchantClient.class)
public class PenchantClientMixin {

    @WrapMethod(method = "getProgressTooltip")
    private static Component showPendingLevelUps(
            EnchantmentProgress progress,
            Holder<Enchantment> enchantment,
            int level,
            DataComponentGetter components,
            Operation<Component> original
    ) {
        if (PenchantLevelUps.isEnabled()) {
            int pendingLevels = PenchantLevelUps.getPendingLevels(enchantment, level, progress.getProgress(enchantment), components);
            if (pendingLevels > 0) {
                return Component.literal("   ")
                        .append(FontUtils.getBar(PenchantClient.getBarWidth(), PenchantClient.getBarWidth()))
                        .append(" ")
                        .append(Component.translatable(
                                "tooltip.affogatocore.penchant_level_up_ready",
                                Component.translatable("enchantment.level." + (level + pendingLevels))
                        ))
                        .withStyle(ChatFormatting.YELLOW);
            }
        }
        return original.call(progress, enchantment, level, components);
    }
}
