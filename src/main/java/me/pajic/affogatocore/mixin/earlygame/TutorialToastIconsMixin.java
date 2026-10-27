package me.pajic.affogatocore.mixin.earlygame;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TutorialToast.Icons.class)
public enum TutorialToastIconsMixin {

    AFFOGATOCORE_LEAVES(AffogatoCore.id("toast/leaves")),
    AFFOGATOCORE_GRAVEL(AffogatoCore.id("toast/gravel")),
    AFFOGATOCORE_FLINT_AXE(AffogatoCore.id("toast/flint_axe"));

    @Shadow TutorialToastIconsMixin(Identifier sprite) {}
}
