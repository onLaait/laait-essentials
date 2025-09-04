package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeightedSoundSet.class)
public class WeightedSoundSetMixin {

    @Mutable
    @Final
    @Shadow
    private Text subtitle;

    @Inject(method = "<init>(Lnet/minecraft/util/Identifier;Ljava/lang/String;)V", at = @At("RETURN"))
    void injected(Identifier id, String subtitle, CallbackInfo ci) {
        if (LaaitEssentials.INSTANCE.getConfig().getShowUntranslatableSubtitles() && this.subtitle == null) {
            this.subtitle = Text.literal(id.toString());
        }
    }
}