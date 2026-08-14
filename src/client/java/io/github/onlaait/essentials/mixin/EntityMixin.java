package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Entity.class)
public class EntityMixin {

    @Final
    @Shadow
    private static EntityDataAccessor<Optional<Component>> DATA_CUSTOM_NAME;

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
}