package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.player.RemotePlayer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RemotePlayer.class)
public class RemotePlayerMixin {

    @Shadow
    private int lerpDeltaMovementSteps;

    @Redirect(method = "lerpMotion", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/RemotePlayer;lerpDeltaMovementSteps:I", opcode = Opcodes.PUTFIELD, ordinal = 0))
    private void injected(RemotePlayer instance, int value) {
        lerpDeltaMovementSteps = LaaitEssentials.INSTANCE.getConfig().getInterpolation().getPlayer();
    }
}