package io.github.onlaait.essentials.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import io.github.onlaait.essentials.system.TransferAlarm
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.network.protocol.common.ClientboundTransferPacket

object TransferCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            literal("ltransfer")
                .then(
                    argument("hostname", StringArgumentType.string())
                        .executes { ctx -> transfer(
                            StringArgumentType.getString(ctx, "hostname"),
                            25565
                        ) }
                        .then(
                            argument("port", IntegerArgumentType.integer(1, 65535))
                                .executes { ctx -> transfer(
                                    StringArgumentType.getString(ctx, "hostname"),
                                    IntegerArgumentType.getInteger(ctx, "port")
                                ) }
                        )
                )
        )
    }

    private fun transfer(hostname: String, port: Int): Int {
        val packet = ClientboundTransferPacket(hostname, port)
        TransferAlarm.throughPacket = packet
        Minecraft.getInstance().connection!!.handleTransfer(packet)

        return 1
    }
}