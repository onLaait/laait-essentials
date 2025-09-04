package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.CustomClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements CustomClientPlayerEntity {

    @Unique
    public int customJumpingCooldown;

    @Override
    public int laaitessentials$getCustomJumpingCooldown() {
        return customJumpingCooldown;
    }

    @Override
    public void laaitessentials$setCustomJumpingCooldown(int value) {
        customJumpingCooldown = value;
    }

    @Unique
    public PlayerInput lastSentPlayerInput = PlayerInput.DEFAULT;

    @Override
    public PlayerInput laaitessentials$getLastSentPlayerInput() {
        return lastSentPlayerInput;
    }

    @Unique
    public boolean releaseJump = false;

    @Override
    public void laaitessentials$setReleaseJump(boolean value) {
        releaseJump = value;
    }

    @Shadow
    private PlayerInput lastPlayerInput;

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/PlayerInput;equals(Ljava/lang/Object;)Z"))
    boolean injected(PlayerInput instance, Object object) {
        ClientPlayerEntity cpe = (ClientPlayerEntity) (Object) this;
        PlayerInput playerInput = cpe.input.playerInput;
        PlayerInput virtualInput = releaseJump ? new PlayerInput(
                playerInput.forward(),
                playerInput.backward(),
                playerInput.left(),
                playerInput.right(),
                false,
                playerInput.sneak(),
                playerInput.sprint()
        ) : playerInput;
        if (!lastSentPlayerInput.equals(virtualInput)) {
            cpe.networkHandler.sendPacket(new PlayerInputC2SPacket(virtualInput));
//            LaaitEssentials.INSTANCE.getLogger().info("Send jump input: {}", virtualInput.jump());
            lastSentPlayerInput = virtualInput;
        }
        lastPlayerInput = playerInput;
        return true;
    }
}

