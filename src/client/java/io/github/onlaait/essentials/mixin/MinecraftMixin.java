package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.BlockWave;
import io.github.onlaait.essentials.system.SwordDance;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void injected(CallbackInfo ci) {
        SwordDance.INSTANCE.tick();
        BlockWave.INSTANCE.tick();
    }
}