package io.github.onlaait.essentials.mixin;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import io.github.onlaait.essentials.system.DownloadAlarm;
import net.minecraft.util.HttpUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.Proxy;
import java.net.URL;
import java.nio.file.Path;
import java.util.Map;

@Mixin(HttpUtil.class)
public class HttpUtilMixin {

	@Inject(method = "downloadFile(Ljava/nio/file/Path;Ljava/net/URL;Ljava/util/Map;Lcom/google/common/hash/HashFunction;Lcom/google/common/hash/HashCode;ILjava/net/Proxy;Lnet/minecraft/util/HttpUtil$DownloadProgressListener;)Ljava/nio/file/Path;", at = @At("HEAD"))
	private static void injected(Path targetDir, URL url, Map<String, String> headers, HashFunction hashFunction, @Nullable HashCode requestedHash, int maxSize, Proxy proxy, HttpUtil.DownloadProgressListener listener, CallbackInfoReturnable<Path> cir) {
		DownloadAlarm.INSTANCE.resourcePack(url.toString());
	}
}