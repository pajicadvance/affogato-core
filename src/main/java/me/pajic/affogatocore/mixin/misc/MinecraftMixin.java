package me.pajic.affogatocore.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.renderpearl.api.device.SurfaceException;
import me.pajic.affogatocore.AffogatoCoreClient;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @WrapOperation(
            method = "renderFrame",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private void suppressSwapchainOutOfDateWarning(Logger logger, String message, Object arg1, Object arg2, Operation<Void> original) {
        if (AffogatoCoreClient.CONFIG.suppressSwapchainOutOfDateWarning.get() && ac$isSwapchainOutOfDate(arg2)) return;
        original.call(logger, message, arg1, arg2);
    }

    @Unique
    private static boolean ac$isSwapchainOutOfDate(Object arg) {
        Throwable throwable = arg instanceof Throwable t ? t : null;
        while (throwable != null) {
            if (throwable instanceof SurfaceException && throwable.getMessage() != null && throwable.getMessage().contains("swapchain out of date")) {
                return true;
            }
            throwable = throwable.getCause();
        }
        return false;
    }
}
