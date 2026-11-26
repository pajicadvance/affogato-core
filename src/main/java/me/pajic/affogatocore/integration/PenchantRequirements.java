package me.pajic.affogatocore.integration;

import archives.tater.penchant.registry.PenchantEnchantmentTags;
import archives.tater.penchant.util.PenchantmentHelper;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantingTableBlock;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;

import java.util.List;
import java.util.stream.Stream;

public class PenchantRequirements {

    public static int getTierValue(Holder<Enchantment> enchantment, List<? extends Integer> tierValues, int original) {
        Integer tier = enchantment.unwrapKey()
                .map(key -> AffogatoCore.CONFIG.modIntegration.penchantEnchantmentTiers.get()
                        .get(key.identifier().toString())
                )
                .orElse(null);
        if (tier == null || tier < 1 || tier > tierValues.size()) return original;
        return tierValues.get(tier - 1);
    }

    public static int countUniqueEnchantments(Level level, BlockPos pos) {
        return (int) PenchantmentHelper.getBookshelfOffsets().stream()
                .filter(offset -> EnchantingTableBlock.isValidBookShelf(level, pos, offset))
                .map(pos::offset)
                .map(level::getBlockEntity)
                .flatMap(entity -> entity instanceof ChiseledBookShelfBlockEntity bookshelf ?
                        bookshelf.getItems().stream() : Stream.empty()
                )
                .flatMap(stack -> PenchantmentHelper.getEnchantments(stack).keySet().stream())
                .filter(enchantment -> !enchantment.is(PenchantEnchantmentTags.DISABLED))
                .distinct()
                .count();
    }
}
