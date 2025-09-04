package com.github.onlaait.essentials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onEntityTrackerUpdate(Lnet/minecraft/network/packet/s2c/play/EntityTrackerUpdateS2CPacket;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;writeUpdatedEntries(Ljava/util/List;)V"))
    void injected(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        if (entity instanceof ClientPlayerEntity) {
            packet.trackedValues().removeIf(entry -> entry.handler().equals(TrackedDataHandlerRegistry.ENTITY_POSE));
        }
    }


/*    @Inject(method = "onEntityPosition(Lnet/minecraft/network/packet/s2c/play/EntityPositionS2CPacket;)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injected1(EntityPositionS2CPacket packet, CallbackInfo ci, Entity entity) {
        LaaitEssentials.INSTANCE.getLogger().info("onEntityPosition " + entity.getType().toString());
    }

    @Inject(method = "onEntity(Lnet/minecraft/network/packet/s2c/play/EntityS2CPacket;)V", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injected2(EntityS2CPacket packet, CallbackInfo ci, Entity entity) {
        LaaitEssentials.INSTANCE.getLogger().info("onEntity " + entity.getType().toString());
    }*/
}