package io.github.onlaait.essentials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.onlaait.essentials.CustomEntity;
import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class HudMixin {

    @Unique
    private static final Identifier TELEPORT_OUTLINE_LOCATION = LaaitEssentials.INSTANCE.id("textures/misc/teleport_outline.png");

    @Shadow
    protected abstract void extractTextureOverlay(final GuiGraphicsExtractor graphics, final Identifier texture, final float alpha);

    @Inject(method = "extractCameraOverlays", at = @At("RETURN"))
    private void injected(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci, @Local LocalPlayer player) {
        this.extractTextureOverlay(graphics, TELEPORT_OUTLINE_LOCATION, ((CustomEntity) player).laaitessentials_getPercentTeleport());
    }
}