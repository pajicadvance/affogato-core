package me.pajic.affogatocore.mixin.stats;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.affogatocore.AffogatoCore;
import me.pajic.affogatocore.util.ToolMaterialId;
import me.pajic.affogatocore.util.ToolStatReplacement;
import net.minecraft.world.item.ToolMaterial;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {

    @ModifyExpressionValue(
            method = "applyCommonProperties",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/item/ToolMaterial;durability:I",
                    opcode = Opcodes.GETFIELD
            )
    )
    private int modifyDurability(int original) {
        ToolMaterial material = (ToolMaterial) (Object) this;
        List<ToolStatReplacement> replacements = AffogatoCore.CONFIG.toolStats.stream().filter(tsr ->
                ToolMaterialId.equal(tsr.material.get(), material)
        ).toList();
        if (!replacements.isEmpty()) {
            ToolStatReplacement replacement = replacements.getFirst();
            if (replacement.modifyDurability.get()) return replacement.durability.get();
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "applyToolProperties",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/item/ToolMaterial;speed:F",
                    opcode = Opcodes.GETFIELD
            )
    )
    private float modifyMiningSpeed(float original) {
        ToolMaterial material = (ToolMaterial) (Object) this;
        List<ToolStatReplacement> replacements = AffogatoCore.CONFIG.toolStats.stream().filter(tsr ->
                ToolMaterialId.equal(tsr.material.get(), material)
        ).toList();
        if (!replacements.isEmpty()) {
            ToolStatReplacement replacement = replacements.getFirst();
            if (replacement.modifyMiningSpeed.get()) return replacement.miningSpeed.get();
        }
        return original;
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void logIds(CallbackInfo ci) {
        ToolMaterial material = (ToolMaterial) (Object) this;
        ToolMaterialId.MATERIAL_IDS.add(ToolMaterialId.of(material));
    }
}
