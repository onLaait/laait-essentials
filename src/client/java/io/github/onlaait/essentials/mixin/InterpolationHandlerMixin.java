package io.github.onlaait.essentials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InterpolationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InterpolationHandler.class)
public class InterpolationHandlerMixin {

    @ModifyVariable(method = "<init>(Lnet/minecraft/world/entity/Entity;ILjava/util/function/Consumer;)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static int injected(int i, @Local(argsOnly = true) Entity entity) {
        return entity instanceof RemotePlayer ?
                LaaitEssentials.INSTANCE.getConfig().getInterpolation().getPlayer()
                : i;
    }
}