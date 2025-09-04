package com.github.onlaait.essentials;

import net.minecraft.util.PlayerInput;

public interface CustomClientPlayerEntity {

    int laaitessentials$getCustomJumpingCooldown();

    void laaitessentials$setCustomJumpingCooldown(int value);

    PlayerInput laaitessentials$getLastSentPlayerInput();

    void laaitessentials$setReleaseJump(boolean value);

}
