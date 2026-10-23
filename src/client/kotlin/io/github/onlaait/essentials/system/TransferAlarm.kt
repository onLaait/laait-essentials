package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials.LOGGER
import io.github.onlaait.essentials.LaaitEssentials.config
import io.github.onlaait.essentials.Utils
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentUtils
import net.minecraft.network.protocol.common.ClientboundTransferPacket

object TransferAlarm {

    var throughPacket: ClientboundTransferPacket? = null

    fun onTransfer(packet: ClientboundTransferPacket): Boolean {
        if (packet == throughPacket) {
            throughPacket = null
            return true
        }
        val host = packet.host
        val port = packet.port
        LOGGER.info("Transfer: $host:$port")
        if (config.rejectTransfer) {
            Utils.showChat(
                Component.empty()
                    .append("Received a transfer request to ")
                    .append(ComponentUtils.copyOnClickText(if (port == 25565) host else "$host:$port"))
                    .append(" ")
                    .append(
                        Component.literal("[Go]")
                            .withStyle {
                                it.withColor(ChatFormatting.GREEN)
                                    .withBold(true)
                                    .withClickEvent(ClickEvent.RunCommand("ltransfer $host $port"))
                            }
                    )
            )
            return false
        }
        return true
    }
}