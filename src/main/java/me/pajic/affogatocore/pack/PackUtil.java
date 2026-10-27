package me.pajic.affogatocore.pack;

import me.pajic.affogatocore.AffogatoCore;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;

public class PackUtil {

    static final ModContainer CONTAINER = FabricLoader.getInstance().getModContainer(AffogatoCore.MOD_ID).orElseThrow();

    static void registerPersistentPack(String id, String name) {
        ResourceLoader.registerBuiltinPack(AffogatoCore.id(id), CONTAINER, Component.literal(name), PackActivationType.ALWAYS_ENABLED);
    }
}
