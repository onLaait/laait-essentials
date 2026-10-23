package io.github.onlaait.essentials.mixin;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @ModifyVariable(method = "setReducedDebugInfo", at = @At("HEAD"), argsOnly = true)
    private boolean injected(boolean reducedDebugInfo) {
        return false;
    }
}