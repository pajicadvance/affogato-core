package me.pajic.affogatocore.mixin.gameplay;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;
import java.util.UUID;

@Mixin(VaultServerData.class)
public interface VaultServerDataAccessor {

    @Invoker("hasRewardedPlayer")
    boolean ac$callHasRewardedPlayer(final Player player);

    @Invoker("markChanged")
    void ac$callMarkChanged();

    @Accessor("rewardedPlayers")
    Set<UUID> ac$getRewardedPlayers();
}
