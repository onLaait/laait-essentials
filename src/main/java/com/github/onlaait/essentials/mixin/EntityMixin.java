package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import com.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Entity.class)
public class EntityMixin {

    @Final
    @Shadow
    private static TrackedData<Optional<Text>> CUSTOM_NAME;

    @Inject(method = "onDataTrackerUpdate(Ljava/util/List;)V", at = @At("HEAD"))
    private void injected(List<DataTracker.SerializedEntry<?>> dataEntries, CallbackInfo ci) {
        for (var serializedEntry : dataEntries) {
            if (serializedEntry.id() == CUSTOM_NAME.id()) {
                Entity entity = ((Entity) (Object) this);
                Text customName = entity.getCustomName();
                if (customName != null && NoLaggyText.INSTANCE.exceededMaxLength(customName)) entity.setCustomName(NoLaggyText.INSTANCE.apply(customName));
                break;
            }
        }
    }

    @ModifyArg(method = "calculateBoundingBox()Lnet/minecraft/util/math/Box;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;calculateDefaultBoundingBox(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Box;"), index = 0)
    Vec3d injected(Vec3d pos) {
        if ((Object) this instanceof LivingEntity le && !(le instanceof ClientPlayerEntity) && le.getWorld().isClient() && LaaitEssentials.INSTANCE.getConfig().getRealTimeHitbox()) {
            LivingEntityAccessor lea = (LivingEntityAccessor) le;
            return new Vec3d(lea.getServerX(), lea.getServerY(), lea.getServerZ());
        }
        return pos;
    }
}