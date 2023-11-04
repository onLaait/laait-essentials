package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void setCustomName(@Nullable Text name);

    @Inject(method = "onDataTrackerUpdate(Ljava/util/List;)V", at = @At("HEAD"))
    private void injected(List<DataTracker.SerializedEntry<?>> dataEntries, CallbackInfo ci) {
        for (DataTracker.SerializedEntry<?> serializedEntry : dataEntries) {
            if (serializedEntry.id() == 2) {
                Optional<Text> optVal = (Optional<Text>) serializedEntry.value();
                if (optVal.isEmpty()) break;
                NoLaggyText.CensorResult censor = NoLaggyText.INSTANCE.censor(optVal.get());
                if (!censor.getCensored()) break;
                this.setCustomName(censor.getText());
                break;
            }
        }
    }
}