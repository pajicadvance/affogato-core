package me.pajic.affogatocore.mixin.integration.penchant;

import archives.tater.penchant.client.gui.widget.EnchantmentSlotWidget;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@IfModLoaded("penchant")
@Mixin(EnchantmentSlotWidget.class)
public interface EnchantmentSlotWidgetAccessor {

    @Accessor("enchantment")
    Holder<Enchantment> ac$getEnchantment();
}
