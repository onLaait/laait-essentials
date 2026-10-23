package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.SignScreenAlarm;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.world.level.block.entity.SignText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSignEditScreen.class)
public class AbstractSignEditScreenMixin {

    @Shadow
    @Final private SignText.Mutable text;

    @Inject(method = "init", at = @At("HEAD"))
    private void injected(CallbackInfo ci) {
        SignScreenAlarm.INSTANCE.handle(text.asImmutable());
    }
}