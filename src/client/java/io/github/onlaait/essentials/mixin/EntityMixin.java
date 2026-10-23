package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.CustomEntity;
import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Entity.class)
public class EntityMixin implements CustomEntity {

    @Final
    @Shadow
    private static EntityDataAccessor<Optional<Component>> DATA_CUSTOM_NAME;

    @Unique
    private int ticksTeleport = 0;

    @Override
    public int laaitessentials_getTicksTeleport() {
        return ticksTeleport;
    }

    @Override
    public void laaitessentials_setTicksTeleport(int value) {
        ticksTeleport = value;
    }

    @Inject(method = "onSyncedDataUpdated(Ljava/util/List;)V", at = @At("HEAD"))
    private void injected(List<SynchedEntityData.DataValue<?>> updatedItems, CallbackInfo ci) {
        for (var dataValue : updatedItems) {
            if (dataValue.id() == DATA_CUSTOM_NAME.id()) {
                Entity entity = (Entity) (Object) this;
                Component customName = entity.getCustomName();
                if (customName != null && NoLaggyText.INSTANCE.exceededMaxLength(customName)) entity.setCustomName(NoLaggyText.INSTANCE.apply(customName));
                break;
            }
        }
    }

    @Redirect(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z", ordinal = 0))
    private boolean injected(Level level) {
        var isClientSide = level.isClientSide();
        if (isClientSide) {
            laaitessentials_setTicksTeleport(Math.max(0, laaitessentials_getTicksTeleport() - 1));
        }
        return isClientSide;
    }


}