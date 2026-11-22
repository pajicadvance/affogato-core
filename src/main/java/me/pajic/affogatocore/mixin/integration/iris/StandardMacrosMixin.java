package me.pajic.affogatocore.mixin.integration.iris;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.irisshaders.iris.gl.shader.StandardMacros;
import net.irisshaders.iris.helpers.StringPair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@IfModLoaded("iris")
@Mixin(value = StandardMacros.class, remap = false)
public class StandardMacrosMixin {

    @ModifyReturnValue(
            method = "createStandardEnvironmentDefines",
            at = @At("RETURN")
    )
    private static ImmutableList<StringPair> enableIceCaveFog(ImmutableList<StringPair> original) {
        return ImmutableList.<StringPair>builder()
                .addAll(original)
                .add(new StringPair("ICE_CAVE_FOG", ""))
                .build();
    }
}
