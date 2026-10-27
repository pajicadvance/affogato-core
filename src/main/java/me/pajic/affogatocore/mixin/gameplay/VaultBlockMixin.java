package me.pajic.affogatocore.mixin.gameplay;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VaultBlock;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VaultBlock.class)
public class VaultBlockMixin {

    @WrapMethod(method = "useItemOn")
    private InteractionResult emeraldBlockResetsVault(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult, Operation<InteractionResult> original) {
        if (AffogatoCore.CONFIG.loot.emeraldBlockResetsVaults.get() && itemStack.is(Items.EMERALD_BLOCK)) {
            if (level instanceof ServerLevel && level.getBlockEntity(pos) instanceof VaultBlockEntity vbe) {
                VaultServerDataAccessor data = (VaultServerDataAccessor) vbe.getServerData();
                if (data != null && data.ac$callHasRewardedPlayer(player)) {
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                    itemStack.consume(1, player);
                    data.ac$getRewardedPlayers().remove(player.getUUID());
                    data.ac$callMarkChanged();
                    return InteractionResult.SUCCESS_SERVER;
                }
            }
        }
        return original.call(itemStack, state, level, pos, player, hand, hitResult);
    }
}
