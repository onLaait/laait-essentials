package laait.essentials.mixin;

import laait.essentials.system.NoLaggyText;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientBossBar.class)
public class ClientBossBarMixin {
    @ModifyVariable(method = "<init>(Ljava/util/UUID;Lnet/minecraft/text/Text;FLnet/minecraft/entity/boss/BossBar$Color;Lnet/minecraft/entity/boss/BossBar$Style;ZZZ)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static Text injected(Text name) {
        return NoLaggyText.INSTANCE.censor(name).getText();
    }
}