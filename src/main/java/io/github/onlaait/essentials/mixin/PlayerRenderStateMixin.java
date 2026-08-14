package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.CustomPlayerRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerRenderState.class)
public class PlayerRenderStateMixin implements CustomPlayerRenderState {

    @Unique
    Vec3 viewFactor;

    @Override
    public Vec3 laaitessentials$getViewFactor() {
        return viewFactor;
    }

    @Override
    public void laaitessentials$setViewFactor(Vec3 value) {
        viewFactor = value;
    }
}