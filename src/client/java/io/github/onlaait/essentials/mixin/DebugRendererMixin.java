package io.github.onlaait.essentials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.onlaait.essentials.EntityViewLineDebugRenderer;
import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {

    @Shadow
    @Final private List<DebugRenderer.SimpleDebugRenderer> renderers;

    @Inject(method = "refreshRendererList", at = @At("RETURN"))
    private void injected(CallbackInfo ci, @Local Minecraft minecraft) {
        if (LaaitEssentials.INSTANCE.getConfig().getViewLine().getEnabled()) {
            renderers.add(new EntityViewLineDebugRenderer(minecraft));
        }
    }
}