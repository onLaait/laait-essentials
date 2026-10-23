package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyParticle;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientboundLevelParticlesPacket.class)
public class ClientboundLevelParticlesPacketMixin {

    @Shadow
    @Mutable
    @Final private int count;

    @Redirect(method = "<init>*", at = @At(value = "FIELD", target = "Lnet/minecraft/network/protocol/game/ClientboundLevelParticlesPacket;count:I", opcode = Opcodes.PUTFIELD))
    private void injected(ClientboundLevelParticlesPacket instance, int value) {
        this.count = NoLaggyParticle.INSTANCE.apply(value);
    }
}