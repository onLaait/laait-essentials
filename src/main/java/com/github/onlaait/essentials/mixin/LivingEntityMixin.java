package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.CustomClientPlayerEntity;
import com.github.onlaait.essentials.LaaitEssentials;
import com.github.onlaait.essentials.config.InputPacketTrick;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyVariable(method = "updateTrackedPositionAndAngles", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int injected(int interpolationSteps) {
        if ((Object) this instanceof PlayerEntity) return LaaitEssentials.INSTANCE.getConfig().getInterpolation().getPlayer();
        return interpolationSteps;
    }

    @ModifyVariable(method = "updateTrackedHeadRotation(FI)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private int injected2(int interpolationSteps) {
        return 2;
    }


    @Shadow
    private int jumpingCooldown;

    @Inject(method = "tickMovement()V", at = @At("HEAD"))
    void injected(CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity cpe) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            int customJumpingCooldown = ccpe.laaitessentials$getCustomJumpingCooldown();
            if (customJumpingCooldown > 0) {
                customJumpingCooldown--;
                ccpe.laaitessentials$setCustomJumpingCooldown(customJumpingCooldown);
            }
            ccpe.laaitessentials$setReleaseJump(false);
        }
    }

    @Inject(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getSwimHeight()D", ordinal = 0))
    void injected2(CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity cpe) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            int customJumpingCooldown = ccpe.laaitessentials$getCustomJumpingCooldown();
            if (customJumpingCooldown < 2 && LaaitEssentials.INSTANCE.getConfig().getCustomJumpingCooldown().getInputPacketTrick() == InputPacketTrick.INSANE) {
                ccpe.laaitessentials$setReleaseJump(true);
            }
        }
    }


    @Inject(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", opcode = Opcodes.PUTFIELD, ordinal = 1))
    void injected3(CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity cpe) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            ccpe.laaitessentials$setCustomJumpingCooldown(LaaitEssentials.INSTANCE.getConfig().getCustomJumpingCooldown().getJumpingCooldown());
        }
    }

    @Inject(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", opcode = Opcodes.PUTFIELD, ordinal = 2))
    void injected4(CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity cpe) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            ccpe.laaitessentials$setCustomJumpingCooldown(0);
        }
    }

    @Redirect(method = "tickMovement()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/LivingEntity;jumpingCooldown:I", opcode = Opcodes.GETFIELD, ordinal = 2))
    int injected(LivingEntity entity) {
        if (entity instanceof ClientPlayerEntity cpe) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            int customJumpingCooldown = ccpe.laaitessentials$getCustomJumpingCooldown();
            if (LaaitEssentials.INSTANCE.getConfig().getCustomJumpingCooldown().getInputPacketTrick() != InputPacketTrick.NONE) {
//                LaaitEssentials.INSTANCE.getLogger().info("jumpingCooldown: {}, customJumpingCooldown: {}", jumpingCooldown, customJumpingCooldown);
                if (jumpingCooldown == 0 && customJumpingCooldown > 0) {
                    ccpe.laaitessentials$setReleaseJump(true);
                    return customJumpingCooldown;
                }
                if (customJumpingCooldown < 2) {
                    if (customJumpingCooldown == 0 && !ccpe.laaitessentials$getLastSentPlayerInput().jump()) {
                        if (LaaitEssentials.INSTANCE.getConfig().getCustomJumpingCooldown().getInputPacketTrick() == InputPacketTrick.INSANE) {
                            ccpe.laaitessentials$setReleaseJump(false);
                        }
                        return 0;
                    }
                    if (jumpingCooldown < 2) return jumpingCooldown;
                    ccpe.laaitessentials$setReleaseJump(true);
                    return 1;
                }
            }
            return customJumpingCooldown;
        }
        return jumpingCooldown;
    }

    @Inject(method = "tickMovement()V", at = @At(value = "RETURN"))
    void injected5(CallbackInfo ci) {
        if ((Object) this instanceof ClientPlayerEntity cpe && LaaitEssentials.INSTANCE.getConfig().getCustomJumpingCooldown().getInputPacketTrick() == InputPacketTrick.INSANE && cpe.getVelocity().y > 0) {
            CustomClientPlayerEntity ccpe = (CustomClientPlayerEntity) cpe;
            ccpe.laaitessentials$setReleaseJump(false);
        }
    }
}