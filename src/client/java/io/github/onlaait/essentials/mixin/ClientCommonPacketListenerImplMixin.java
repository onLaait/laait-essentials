package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.TransferAlarm;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.common.ClientboundTransferPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonPacketListenerImplMixin {

    @Inject(method = "handleTransfer", at = @At(value = "HEAD"), cancellable = true)
    private void handleTransfer(ClientboundTransferPacket packet, CallbackInfo ci) {
        if (!TransferAlarm.INSTANCE.onTransfer(packet)) ci.cancel();
    }
}