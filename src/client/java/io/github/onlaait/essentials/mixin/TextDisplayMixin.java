package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Display;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Display.TextDisplay.class)
public abstract class TextDisplayMixin {

    @Shadow
    @Final private static EntityDataAccessor<Component> DATA_TEXT_ID;

    @Shadow
    public abstract Component getText();

    @Shadow
    public abstract void setText(Component component);

    @Inject(method = "onSyncedDataUpdated(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", at = @At("RETURN"))
    private void injected(EntityDataAccessor<?> entityDataAccessor, CallbackInfo ci) {
        if (entityDataAccessor.id() == DATA_TEXT_ID.id()) {
            Component text = getText();
            if (NoLaggyText.INSTANCE.exceededMaxLength(text)) setText(NoLaggyText.INSTANCE.apply(text));
        }
    }
}