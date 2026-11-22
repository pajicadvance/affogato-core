package me.pajic.affogatocore;

import net.fabricmc.loader.api.FabricLoader;

public class CompatFlags {
    public static final boolean CLIFFTREE_LOADED = FabricLoader.getInstance().isModLoaded("clifftree");
    public static final boolean EMBERS_AND_ICICLES_LOADED = FabricLoader.getInstance().isModLoaded("embers_and_icicles");
    public static final boolean IRIS_LOADED = FabricLoader.getInstance().isModLoaded("iris");
    public static final boolean FD_LOADED = FabricLoader.getInstance().isModLoaded("farmersdelight");
    public static final boolean PENCHANT_LOADED = FabricLoader.getInstance().isModLoaded("penchant");
    public static final boolean TOIL_AND_TROUBLE_LOADED = FabricLoader.getInstance().isModLoaded("toil_and_trouble");
    public static final boolean VARIANTS_AND_VENTURES_LOADED = FabricLoader.getInstance().isModLoaded("variantsandventures");
}
