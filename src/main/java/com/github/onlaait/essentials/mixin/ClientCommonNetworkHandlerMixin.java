package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.s2c.common.ServerTransferS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {

    @Inject(method = "onServerTransfer(Lnet/minecraft/network/packet/s2c/common/ServerTransferS2CPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handleDisconnection()V", shift = At.Shift.AFTER), cancellable = true)
    void injected(ServerTransferS2CPacket packet, CallbackInfo ci) {
        if (LaaitEssentials.INSTANCE.getConfig().getRejectTransfer()) {
            LaaitEssentials.INSTANCE.getLogger().info("Rejected transfer: {}, {}", packet.host(), packet.port());
            ci.cancel();
        }
    }
}