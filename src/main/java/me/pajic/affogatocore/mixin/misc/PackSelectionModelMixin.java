package me.pajic.affogatocore.mixin.misc;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PackSelectionModel.class)
public class PackSelectionModelMixin {

    @Shadow @Final private List<Pack> selected;
    @Shadow @Final private List<Pack> unselected;

    @Inject(
            method = "findNewPacks",
            at = @At("TAIL")
    )
    private void filterPacks(CallbackInfo ci) {
        selected.removeIf(pack -> pack.getId().contains(AffogatoCore.MOD_ID));
        unselected.removeIf(pack -> pack.getId().contains(AffogatoCore.MOD_ID));
    }
}
