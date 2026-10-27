package me.pajic.affogatocore.util;

import net.minecraft.world.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class ToolMaterialId {

    public static final List<String> MATERIAL_IDS = new ArrayList<>();

    public static String of(ToolMaterial material) {
        return material.incorrectBlocksForDrops().location() + "," +
                material.repairItems().location() + "," +
                material.durability() + "," +
                material.speed() + "," +
                material.attackDamageBonus() + "," +
                material.enchantmentValue();
    }

    public static boolean equal(String id, ToolMaterial material) {
       return of(material).equals(id);
    }
}
