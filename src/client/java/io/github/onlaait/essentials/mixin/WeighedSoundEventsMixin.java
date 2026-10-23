package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeighedSoundEvents.class)
public class WeighedSoundEventsMixin {

    @Mutable
    @Final
    @Shadow
    private Component subtitle;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void injected(Identifier location, String subtitle, CallbackInfo ci) {
        if (LaaitEssentials.INSTANCE.getConfig().getShowUntranslatableSubtitles() && this.subtitle == null) {
            this.subtitle = Component.literal(location.toString());
        }
    }
}