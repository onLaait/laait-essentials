package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossBar.class)
public class BossBarMixin {

    @ModifyVariable(method = "setName(Lnet/minecraft/text/Text;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    Text injected(Text name) {
        return (Object) this instanceof ClientBossBar && NoLaggyText.INSTANCE.exceededMaxLength(name) ? NoLaggyText.INSTANCE.apply(name) : name;
    }
}