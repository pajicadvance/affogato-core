package me.pajic.affogatocore.mixin.trade;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.trade.WanderingTraderPools;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderMixin extends AbstractVillager {

    public WanderingTraderMixin(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "updateTrades")
    private void replaceTrades(ServerLevel level, Operation<Void> original) {
        if (AffogatoCore.CONFIG.packs.customWanderingTraderTrades.get()) {
            MerchantOffers offers = getOffers();
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_XP_BOTTLE);
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_CONVERSION);
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_CROP);
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_SEED);
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_SAPLING);
            addOffersFromTradeSet(level, offers, WanderingTraderPools.WANDERING_TRADER_DYE);
        }
        else original.call(level);
    }
}
