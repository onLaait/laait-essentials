package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.CustomPlayerRenderState;
import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;F)V", at = @At("RETURN"))
    private void injected(AbstractClientPlayer abstractClientPlayer, PlayerRenderState playerRenderState, float f, CallbackInfo ci) {
        var cprs = (CustomPlayerRenderState) playerRenderState;
        if (LaaitEssentials.INSTANCE.getConfig().getViewLine().getEnabled()) {
            Vec3 vec3 = abstractClientPlayer.getViewVector(f);
            cprs.laaitessentials$setViewFactor(vec3);
        } else {
            cprs.laaitessentials$setViewFactor(null);
        }
    }
}