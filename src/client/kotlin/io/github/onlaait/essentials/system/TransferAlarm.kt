package io.github.onlaait.essentials.system

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.ClickEvent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.protocol.common.ClientboundTransferPacket

object TransferAlarm {

    var throughPacket: ClientboundTransferPacket? = null

    fun handle(packet: ClientboundTransferPacket) {
        val host = packet.host
        val port = packet.port
        Minecraft.getInstance().player!!.displayClientMessage(
            Component.empty()
                .append("Received a transfer request to $host:$port ")
                .append(
                    Component.literal("[Go]")
                        .withStyle(
                            Style.EMPTY
                                .withColor(ChatFormatting.GREEN)
                                .withBold(true)
                                .withClickEvent(ClickEvent.RunCommand("ltransfer $host $port"))
                        )
                ),
            false
        )
    }
}