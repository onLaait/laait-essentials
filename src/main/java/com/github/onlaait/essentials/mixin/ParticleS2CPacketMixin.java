package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
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
    @Shadow
    @Final
    private int count;

    @Redirect(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/network/packet/s2c/play/ParticleS2CPacket;count:I", opcode = Opcodes.PUTFIELD))
    private void injected(ParticleS2CPacket instance, int value) {
        int maxCount = 3000;
        if (value > maxCount) {
            count = maxCount;
            LaaitEssentials.INSTANCE.getLogger().warn("[Laait Essentials] Mitigated high-count particle: {} -> {}", value, maxCount);
        }
    }
}