package me.pajic.affogatocore.util;

import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

import java.util.List;

public class ItemStatModification {
    public ValidatedList<String> items;
    public ValidatedBoolean modifyDurability;
    public ValidatedInt durability;
    public ValidatedBoolean modifyMiningSpeed;
    public ValidatedFloat miningSpeed;
    public ValidatedBoolean modifyAttackDamage;
    public ValidatedFloat attackDamage;
    public ValidatedBoolean modifyAttackSpeed;
    public ValidatedFloat attackSpeed;

    public ItemStatModification(
            List<String> items,
            boolean modifyDurability,
            int durability,
            boolean modifyMiningSpeed,
            float miningSpeed,
            boolean modifyAttackDamage,
            float attackDamage,
            boolean modifyAttackSpeed,
            float attackSpeed
    ) {
        this.items = new ValidatedString("").toList(items);
        this.modifyDurability = new ValidatedBoolean(modifyDurability);
        this.durability = new ValidatedInt(durability, Integer.MAX_VALUE, 1);
        this.modifyMiningSpeed = new ValidatedBoolean(modifyMiningSpeed);
        this.miningSpeed = new ValidatedFloat(miningSpeed);
        this.modifyAttackDamage = new ValidatedBoolean(modifyAttackDamage);
        this.attackDamage = new ValidatedFloat(attackDamage);
        this.modifyAttackSpeed = new ValidatedBoolean(modifyAttackSpeed);
        this.attackSpeed = new ValidatedFloat(attackSpeed);
    }

    public ItemStatModification() {
        this(List.of(), false, 1, false, 1, false, 0, false, 0);
    }

    public static ItemStatModification durability(int durability, String... items) {
        return new ItemStatModification(List.of(items), true, durability, false, 1, false, 0, false, 0);
    }

    public static ItemStatModification durabilityAndMiningSpeed(int durability, float miningSpeed, String... items) {
        return new ItemStatModification(List.of(items), true, durability, true, miningSpeed, false, 0, false, 0);
    }

    public static ItemStatModification attackSpeed(float attackSpeed, String... items) {
        return new ItemStatModification(List.of(items), false, 1, false, 1, false, 0, true, attackSpeed);
    }
}
