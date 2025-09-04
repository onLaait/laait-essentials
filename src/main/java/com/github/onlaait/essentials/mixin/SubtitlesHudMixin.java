package com.github.onlaait.essentials.mixin;

import net.minecraft.client.gui.hud.SubtitlesHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SubtitlesHud.class)
public class SubtitlesHudMixin {

    @ModifyConstant(method = "render(Lnet/minecraft/client/gui/DrawContext;)V", constant = @Constant(floatValue = 75.0F))
    private float injected(float constant) {
        return 100f;
    }
}