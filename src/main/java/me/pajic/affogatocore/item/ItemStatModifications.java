package me.pajic.affogatocore.item;

import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.ItemStatModification;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ItemStatModifications {

    public static void init() {
        DefaultItemComponentEvents.MODIFY.register(context -> {
            List<ItemStatModification> modifications = List.copyOf(AffogatoCore.CONFIG.itemStats);
            if (modifications.isEmpty()) return;
            Map<ItemStatModification, Set<Item>> targets = new HashMap<>();
            context.modify(_ -> true, (builder, registries, item) -> {
                for (ItemStatModification modification : modifications) {
                    if (targets.computeIfAbsent(modification, m -> resolveTargets(m, registries)).contains(item)) {
                        apply(modification, builder);
                    }
                }
            });
        });
    }

    private static Set<Item> resolveTargets(ItemStatModification modification, HolderLookup.Provider registries) {
        Set<Item> items = new HashSet<>();
        for (String entry : modification.items) {
            boolean isTag = entry.startsWith("#");
            Identifier id = Identifier.tryParse(isTag ? entry.substring(1) : entry);
            if (id == null) {
                AffogatoCore.LOGGER.warn("Invalid item or tag id in item stat modifications: {}", entry);
                continue;
            }
            if (isTag) {
                registries.lookupOrThrow(Registries.ITEM).get(TagKey.create(Registries.ITEM, id))
                        .ifPresent(tag -> tag.forEach(holder -> items.add(holder.value())));
            } else {
                BuiltInRegistries.ITEM.getOptional(id).ifPresent(items::add);
            }
        }
        return items;
    }

    private static void apply(ItemStatModification modification, DataComponentMap.Builder builder) {
        if (modification.modifyDurability.get()) {
            builder.set(DataComponents.MAX_DAMAGE, modification.durability.get());
            builder.set(DataComponents.MAX_STACK_SIZE, 1);
            builder.set(DataComponents.DAMAGE, 0);
        }
        if (modification.modifyMiningSpeed.get() && builder.contains(DataComponents.TOOL)) {
            Tool tool = builder.get(DataComponents.TOOL);
            float speed = modification.miningSpeed.get();
            builder.set(DataComponents.TOOL, new Tool(
                    tool.rules().stream().map(rule -> rule.speed().isPresent() && rule.correctForDrops().orElse(false) ?
                            new Tool.Rule(rule.blocks(), Optional.of(speed), rule.correctForDrops()) : rule
                    ).toList(),
                    tool.defaultMiningSpeed(),
                    tool.damagePerBlock(),
                    tool.canDestroyBlocksInCreative()
            ));
        }
        if (modification.modifyAttackDamage.get()) {
            setBaseModifier(builder, Attributes.ATTACK_DAMAGE, Item.BASE_ATTACK_DAMAGE_ID, modification.attackDamage.get());
        }
        if (modification.modifyAttackSpeed.get()) {
            setBaseModifier(builder, Attributes.ATTACK_SPEED, Item.BASE_ATTACK_SPEED_ID, modification.attackSpeed.get());
        }
    }

    private static void setBaseModifier(DataComponentMap.Builder builder, Holder<Attribute> attribute, Identifier id, double amount) {
        ItemAttributeModifiers modifiers = builder.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        AttributeModifier modifier = new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE);
        if (modifiers.modifiers().stream().noneMatch(entry -> entry.matches(attribute, id))) {
            builder.set(DataComponents.ATTRIBUTE_MODIFIERS, modifiers.withModifierAdded(attribute, modifier, EquipmentSlotGroup.MAINHAND));
            return;
        }
        builder.set(DataComponents.ATTRIBUTE_MODIFIERS, new ItemAttributeModifiers(modifiers.modifiers().stream().map(entry ->
                entry.matches(attribute, id) ? new ItemAttributeModifiers.Entry(attribute, modifier, entry.slot(), entry.display()) : entry
        ).toList()));
    }
}
