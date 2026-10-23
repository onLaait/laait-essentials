package io.github.onlaait.essentials.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net.minecraft.client.gui.components.SubtitleOverlay$Subtitle")
public class SubtitleOverlayMixin {

    @ModifyConstant(method = "getBrightness", constant = @Constant(floatValue = 75.0F))
    private float injected(float constant) {
        return 100;
    }
}