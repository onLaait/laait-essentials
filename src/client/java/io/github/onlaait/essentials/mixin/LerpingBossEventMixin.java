package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LerpingBossEvent.class)
public class LerpingBossEventMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Component injected(Component name) {
        return NoLaggyText.INSTANCE.exceededMaxLength(name) ? NoLaggyText.INSTANCE.apply(name) : name;
    }
}