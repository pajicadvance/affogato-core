package me.pajic.affogatocore.integration;

import archives.tater.penchant.component.EnchantmentProgress;
import me.pajic.affogatocore.AffogatoCore;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class PenchantProgressEvents {

    private static final TagKey<Item> KNIVES = TagKey.create(Registries.ITEM, Identifier.parse("c:tools/knife"));

    public static void init() {
        if (AffogatoCore.CONFIG.modIntegration.penchantProgressOnHostileKill.get()) {
            ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((level, killer, victim, _) -> {
                if (killer instanceof Player player && !(victim instanceof EnderDragon)) {
                    ItemStack weapon = player.getWeaponItem();
                    int xp = victim.getExperienceReward(level, killer);
                    if (victim instanceof Enemy) {
                        Arrays.stream(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).forEach(slot ->
                                EnchantmentProgress.addToProgress(player.getItemBySlot(slot), xp / 3, player)
                        );
                        if (weapon.is(ConventionalItemTags.MELEE_WEAPON_TOOLS) || weapon.is(ConventionalItemTags.RANGED_WEAPON_TOOLS)) {
                            EnchantmentProgress.addToProgress(weapon, Math.max(1, xp / 3), player);
                        }
                    }
                    if (victim instanceof Animal && weapon.is(KNIVES)) {
                        EnchantmentProgress.addToProgress(weapon, Math.max(1, xp), player);
                    }
                }
            });
        }
    }
}
