package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossEvent.class)
public class BossEventMixin {

    @ModifyVariable(method = "setName(Lnet/minecraft/network/chat/Component;)V", at = @At("HEAD"), argsOnly = true)
    private Component injected(Component name) {
        return (BossEvent) (Object) this instanceof LerpingBossEvent && NoLaggyText.INSTANCE.exceededMaxLength(name) ? NoLaggyText.INSTANCE.apply(name) : name;
    }
}