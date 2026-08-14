package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials.LOGGER
import io.github.onlaait.essentials.Utils
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object DownloadAlarm {

    fun resourcePack(url: String) {
        val msg = "Downloading resource pack from $url"
        val player = Minecraft.getInstance().player
        if (player == null) {
            LOGGER.info(msg)
        } else {
            Utils.showChat(Component.literal(msg).withStyle(ChatFormatting.GRAY))
        }
    }
}