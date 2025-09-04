package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyParticle;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ParticleS2CPacket.class)
public class ParticleS2CPacketMixin {

    @Mutable
    @Final
    @Shadow private int count;

    @Redirect(method = "<init>(Lnet/minecraft/particle/ParticleEffect;ZZDDDFFFFI)V", at = @At(value = "FIELD", target = "Lnet/minecraft/network/packet/s2c/play/ParticleS2CPacket;count:I", opcode = Opcodes.PUTFIELD))
    private void injected(ParticleS2CPacket instance, int value) {
        this.count = NoLaggyParticle.INSTANCE.apply(value);
    }

    @Redirect(method = "<init>(Lnet/minecraft/network/RegistryByteBuf;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/network/packet/s2c/play/ParticleS2CPacket;count:I", opcode = Opcodes.PUTFIELD))
    private void injected2(ParticleS2CPacket instance, int value) {
        this.count = NoLaggyParticle.INSTANCE.apply(value);
    }
}