package io.github.onlaait.essentials.mixin;

import io.github.onlaait.essentials.system.AddressIndicator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {

    @Inject(method = "connect(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/multiplayer/resolver/ServerAddress;Lnet/minecraft/client/multiplayer/ServerData;Lnet/minecraft/client/multiplayer/TransferState;)V", at = @At("HEAD"))
    private void injected(Minecraft minecraft, ServerAddress serverAddress, ServerData serverData, TransferState transferState, CallbackInfo ci) {
        AddressIndicator.INSTANCE.onConnect(serverAddress);
    }

    @Redirect(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawCenteredString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"))
    private void injected(GuiGraphics guiGraphics, Font font, Component component, int i, int j, int k) {
        guiGraphics.drawCenteredString(font, AddressIndicator.INSTANCE.getComponent(), i, j - 30, k);
        guiGraphics.drawCenteredString(font, component, i, j, k);
    }
}