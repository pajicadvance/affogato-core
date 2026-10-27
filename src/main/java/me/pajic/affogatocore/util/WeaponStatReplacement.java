package me.pajic.affogatocore.util;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;

public class WeaponStatReplacement {

    public ValidatedString material;
    public ValidatedEnum<WeaponType> type;
    public ValidatedBoolean modifyAttackDamage;
    public ValidatedFloat attackDamage;
    public ValidatedBoolean modifyAttackSpeed;
    public ValidatedFloat attackSpeed;

    public WeaponStatReplacement(
            String material,
            WeaponType type,
            boolean modifyAttackDamage,
            float attackDamage,
            boolean modifyAttackSpeed,
            float attackSpeed
    ) {
        this.material = new ValidatedString(material);
        this.type = new ValidatedEnum<>(type);
        this.modifyAttackDamage = new ValidatedBoolean(modifyAttackDamage);
        this.attackDamage = new ValidatedFloat(attackDamage);
        this.modifyAttackSpeed = new ValidatedBoolean(modifyAttackSpeed);
        this.attackSpeed = new ValidatedFloat(attackSpeed);
    }

    public WeaponStatReplacement(String material, WeaponType type, float attackDamage, float attackSpeed) {
        this(material, type, true, attackDamage, true, attackSpeed);
    }

    public WeaponStatReplacement(String material, float attackDamage, WeaponType type) {
        this(material, type, true, attackDamage, false, 1);
    }

    public WeaponStatReplacement(String material, WeaponType type, float attackSpeed) {
        this(material, type, false, 1, true, attackSpeed);
    }

    public WeaponStatReplacement() {
        this("", WeaponType.SWORD, false, 1, false, 1);
    }
}
