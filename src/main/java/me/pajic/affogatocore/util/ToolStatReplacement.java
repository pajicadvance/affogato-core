package me.pajic.affogatocore.util;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;

public class ToolStatReplacement {

    public ValidatedString material;
    public ValidatedBoolean modifyDurability;
    public ValidatedInt durability;
    public ValidatedBoolean modifyMiningSpeed;
    public ValidatedFloat miningSpeed;

    public ToolStatReplacement(String material, boolean modifyDurability, int durability, boolean modifyMiningSpeed, float miningSpeed) {
        this.material = new ValidatedString(material);
        this.modifyDurability = new ValidatedBoolean(modifyDurability);
        this.durability = new ValidatedInt(durability);
        this.modifyMiningSpeed = new ValidatedBoolean(modifyMiningSpeed);
        this.miningSpeed = new ValidatedFloat(miningSpeed);
    }

    public ToolStatReplacement(String material, int durability, float miningSpeed) {
        this(material, true, durability, true, miningSpeed);
    }

    public ToolStatReplacement(String material, int durability) {
        this(material, true, durability, false, 1);
    }

    public ToolStatReplacement(String material, float miningSpeed) {
        this(material, false, 1, true, miningSpeed);
    }

    public ToolStatReplacement() {
        this("", false, 1, false, 1);
    }
}
