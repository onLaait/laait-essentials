package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.BlockWave;
import io.github.onlaait.essentials.system.SwordDance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Redirect(method = "showOnlyReducedInfo()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isReducedDebugInfo()Z"))
    private boolean injected(LocalPlayer instance) {
        return false;
    }

    @Inject(method = "handleKeybinds()V", at = @At("HEAD"))
    private void injected(CallbackInfo ci) {
        SwordDance.INSTANCE.tick();
        BlockWave.INSTANCE.tick();
    }
}