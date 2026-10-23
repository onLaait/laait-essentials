package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.CustomEntity;
import io.github.onlaait.essentials.system.Bedwars;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.Relative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

/*    @Inject(method = "handleSetEntityData(Lnet/minecraft/network/protocol/game/ClientboundSetEntityDataPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/syncher/SynchedEntityData;assignValues(Ljava/util/List;)V"))
    private void injected(ClientboundSetEntityDataPacket clientboundSetEntityDataPacket, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof LocalPlayer) {
            clientboundSetEntityDataPacket.packedItems().removeIf(entry -> entry.serializer().equals(EntityDataSerializers.POSE));
        }
    }*/

    @Inject(method = "handleMovePlayer(Lnet/minecraft/network/protocol/game/ClientboundPlayerPositionPacket;)V", at = @At(value = "RETURN"))
    private void injected(ClientboundPlayerPositionPacket packet, CallbackInfo ci) {
        Bedwars.INSTANCE.onMovePlayer();
    }

    @Inject(method = "setValuesFromPositionPacket", at = @At(value = "HEAD"))
    private static void injected(PositionMoveRotation change, Set<Relative> relatives, Entity entity, boolean interpolate, CallbackInfoReturnable<Boolean> cir) {
        ((CustomEntity) entity).laaitessentials_setTicksTeleport(10);
    }
}