package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.LaaitEssentials;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.NetworkUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

@Mixin(NetworkUtils.class)
public class NetworkUtilsMixin {

	@Inject(method = "download(Ljava/nio/file/Path;Ljava/net/URL;Ljava/util/Map;Lcom/google/common/hash/HashFunction;Lcom/google/common/hash/HashCode;ILjava/net/Proxy;Lnet/minecraft/util/NetworkUtils$DownloadListener;)Ljava/nio/file/Path;", at = @At("HEAD"))
	private static void download(Path path, URL url, Map<String, String> headers, HashFunction hashFunction, HashCode hashCode, int maxBytes, Proxy proxy, NetworkUtils.DownloadListener listener, CallbackInfoReturnable<Path> cir) {
		String msg = "Downloading file from " + url.toString();
		LaaitEssentials.INSTANCE.getLogger().info(msg);
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (player != null) player.sendMessage(Text.literal(msg).setStyle(Style.EMPTY.withColor(Formatting.GRAY)), false);
	}
}