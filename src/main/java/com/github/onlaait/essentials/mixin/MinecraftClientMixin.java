package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import com.github.onlaait.essentials.system.SwordDance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.SwordItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(method = "hasReducedDebugInfo()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasReducedDebugInfo()Z"))
    boolean injected(ClientPlayerEntity instance) {
        return false;
    }

    @Inject(method = "handleInputEvents()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;attackKey:Lnet/minecraft/client/option/KeyBinding;", opcode = Opcodes.GETFIELD, ordinal = 1))
    void injected(CallbackInfo ci) {
        if (!LaaitEssentials.INSTANCE.getConfig().getSwordDance()) return;
        KeyBinding attackKey = ((MinecraftClient) (Object) this).options.attackKey;
        if (attackKey.isPressed() && MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof SwordItem) {
            boolean dance = SwordDance.INSTANCE.onSwording();
            if (dance) {
                ((KeyBindingAccessor) attackKey).setTimesPressed(1);
            }
        } else {
            SwordDance.INSTANCE.onNotSwording();
        }
    }

    @Redirect(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z", ordinal = 4))
    boolean injected(KeyBinding attackKey) {
        if (LaaitEssentials.INSTANCE.getConfig().getSwordDance() && MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof SwordItem) {
            return attackKey.isPressed() && SwordDance.INSTANCE.wasSwording();
        }
        return attackKey.isPressed();
    }
}