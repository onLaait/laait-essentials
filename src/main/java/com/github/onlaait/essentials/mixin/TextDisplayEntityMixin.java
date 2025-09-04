package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayEntity.TextDisplayEntity.class)
public abstract class TextDisplayEntityMixin {

    @Final
    @Shadow
    private static TrackedData<Text> TEXT;

    @Shadow
    public abstract Text getText();

    @Shadow
    public abstract void setText(Text text);

    @Inject(method = "onTrackedDataSet(Lnet/minecraft/entity/data/TrackedData;)V", at = @At("RETURN"))
    private void injected(TrackedData<?> data, CallbackInfo ci) {
        if (data.id() == TEXT.id()) {
            Text text = getText();
            if (NoLaggyText.INSTANCE.exceededMaxLength(text)) setText(NoLaggyText.INSTANCE.apply(text));
        }
    }
}