package me.pajic.affogatocore.mixin.stats;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.affogatocore.AffogatoCore;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.equipment.ArmorType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorType.class)
public class ArmorTypeMixin {

    @Shadow @Final @Mutable private int unitDurability;

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void setBaseDurabilities(String string, int ordinal, EquipmentSlot slot, int dur, String name, CallbackInfo ci) {
        switch (ordinal) {
            case 0: {
                unitDurability = AffogatoCore.CONFIG.durabilities.helmetBase.get();
                break;
            }
            case 1, 4: {
                unitDurability = AffogatoCore.CONFIG.durabilities.bodyBase.get();
                break;
            }
            case 2: {
                unitDurability = AffogatoCore.CONFIG.durabilities.leggingsBase.get();
                break;
            }
            case 3: {
                unitDurability = AffogatoCore.CONFIG.durabilities.bootsBase.get();
                break;
            }
        }
    }

    @WrapMethod(method = "getDurability")
    private int modifyDurabilityMultipliers(int multiplier, Operation<Integer> original) {
        return switch (multiplier) {
            case 4 -> original.call(AffogatoCore.CONFIG.durabilities.wolfArmorMult.get());
            case 5 -> original.call(AffogatoCore.CONFIG.durabilities.leatherArmorMult.get());
            case 7 -> original.call(AffogatoCore.CONFIG.durabilities.goldenArmorMult.get());
            case 11 -> original.call(AffogatoCore.CONFIG.durabilities.copperArmorMult.get());
            case 15 -> original.call(AffogatoCore.CONFIG.durabilities.ironArmorMult.get());
            case 25 -> original.call(AffogatoCore.CONFIG.durabilities.turtleArmorMult.get());
            case 33 -> original.call(AffogatoCore.CONFIG.durabilities.diamondArmorMult.get());
            case 37 -> original.call(AffogatoCore.CONFIG.durabilities.netheriteArmorMult.get());
            default -> original.call(multiplier);
        };
    }
}
