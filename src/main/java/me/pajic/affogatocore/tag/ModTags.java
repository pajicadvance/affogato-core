package me.pajic.affogatocore.tag;

import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;

public class ModTags {

    public static final TagKey<Item> FLINT_TOOL_MATERIALS = TagKey.create(
            Registries.ITEM,
            AffogatoCore.id("flint_tool_materials")
    );
    public static final TagKey<DamageType> NO_EAT_CANCEL = TagKey.create(
            Registries.DAMAGE_TYPE,
            AffogatoCore.id("no_eat_cancel")
    );
}
