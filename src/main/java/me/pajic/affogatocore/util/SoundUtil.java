package me.pajic.affogatocore.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SoundUtil {

    public static boolean isInRange(double x, double y, double z, int range) {
        if (range <= 0) return true;
        Player player = Minecraft.getInstance().player;
        return player != null && player.distanceToSqr(x, y, z) <= (double) range * range;
    }

    public static int scaleInterval(int interval, float frequencyMult) {
        return Math.max(1, Math.round(interval / frequencyMult));
    }
}
