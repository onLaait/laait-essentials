package io.github.onlaait.essentials.mixin;

import net.minecraft.client.gui.components.SubtitleOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SubtitleOverlay.class)
public class SubtitleOverlayMixin {

    @ModifyConstant(method = "extractRenderState", constant = @Constant(floatValue = 75.0F))
    private float injected(float constant) {
        return 100f;
    }
}