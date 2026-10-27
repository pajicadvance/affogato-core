package me.pajic.affogatocore.mixin.xp;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Blocks.class)
public class BlocksMixin {

    @ModifyArgs(
            method = "lambda$static$25",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setCoalOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.coal.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.coal.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$26",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDeepslateCoalOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.coal.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.coal.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$27",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setNetherGoldOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.netherGold.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.netherGold.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$42",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setLapisOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.lapis.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.lapis.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$43",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDeepslateLapisOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.lapis.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.lapis.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$78",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDiamondOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.diamond.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.diamond.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$79",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDeepslateDiamondOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.diamond.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.diamond.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$193",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setEmeraldOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.emerald.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.emerald.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$194",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setDeepslateEmeraldOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.emerald.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.emerald.get().component2());
    }

    @ModifyArgs(
            method = "lambda$static$252",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/valueproviders/UniformInt;of(II)Lnet/minecraft/util/valueproviders/UniformInt;"
            )
    )
    private static void setQuartzOreXpDropAmount(Args args) {
        args.set(0, AffogatoCore.CONFIG.experience.quartz.get().component1());
        args.set(1, AffogatoCore.CONFIG.experience.quartz.get().component2());
    }
}
