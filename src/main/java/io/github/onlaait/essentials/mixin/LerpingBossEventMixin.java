package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LerpingBossEvent.class)
public class LerpingBossEventMixin {

    @ModifyVariable(method = "<init>(Ljava/util/UUID;Lnet/minecraft/network/chat/Component;FLnet/minecraft/world/BossEvent$BossBarColor;Lnet/minecraft/world/BossEvent$BossBarOverlay;ZZZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static Component injected(Component component) {
        return NoLaggyText.INSTANCE.exceededMaxLength(component) ? NoLaggyText.INSTANCE.apply(component) : component;
    }
}