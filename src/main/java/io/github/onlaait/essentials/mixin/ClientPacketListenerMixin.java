package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.Bedwars;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

/*    @Inject(method = "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;assignValues(Ljava/util/List;)V"))
    private void injected(ClientboundSetEntityDataPacket clientboundSetEntityDataPacket, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof LocalPlayer) {
            clientboundSetEntityDataPacket.packedItems().removeIf(entry -> entry.serializer().equals(EntityDataSerializers.POSE));
        }
    }*/

    @Inject(method = "handleMovePlayer(Lnet/minecraft/network/protocol/game/ClientboundPlayerPositionPacket;)V", at = @At(value = "RETURN"))
    private void injected(ClientboundPlayerPositionPacket clientboundPlayerPositionPacket, CallbackInfo ci) {
        Bedwars.INSTANCE.onMovePlayer();
    }
}