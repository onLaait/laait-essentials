package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.entity.vehicle.DefaultMinecartController;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultMinecartController.class)
public abstract class DefaultMinecartControllerMixin {

    @Shadow
    private int step;

    @Redirect(method = "setPos(DDDFFI)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/vehicle/DefaultMinecartController;step:I", opcode = Opcodes.PUTFIELD))
    void injected(DefaultMinecartController instance, int value) {
        this.step = LaaitEssentials.INSTANCE.getConfig().getInterpolation().getMinecart();
    }
}