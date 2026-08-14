package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.LaaitEssentials;
import io.github.onlaait.essentials.system.TransferAlarm;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.common.ClientboundTransferPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonPacketListenerImplMixin {

    @Inject(method = "handleTransfer(Lnet/minecraft/network/protocol/common/ClientboundTransferPacket;)V", at = @At(value = "HEAD"), cancellable = true)
    private void handleTransfer(ClientboundTransferPacket clientboundTransferPacket, CallbackInfo ci) {
        if (clientboundTransferPacket == TransferAlarm.INSTANCE.getThroughPacket()) {
            TransferAlarm.INSTANCE.setThroughPacket(null);
            return;
        }
        LaaitEssentials.INSTANCE.getLOGGER().info("Transfer: {}:{}", clientboundTransferPacket.host(), clientboundTransferPacket.port());
        if (LaaitEssentials.INSTANCE.getConfig().getRejectTransfer()) {
            TransferAlarm.INSTANCE.handle(clientboundTransferPacket);
            ci.cancel();
        }
    }
}