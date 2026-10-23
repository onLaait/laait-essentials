package io.github.onlaait.essentials.command

import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommands.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component

object ListCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            literal("llist")
                .executes { context ->
                    val playerInfos = Minecraft.getInstance().player!!.connection.listedOnlinePlayers
                    val levelPlayers = Minecraft.getInstance().level!!.players()
                    for (p in playerInfos) {
                        val levelPlayer = levelPlayers.find { it.gameProfile == p.profile }
                        context.source.sendFeedback(
                            Component.empty()
                                .append("- displayName: ").append(levelPlayer?.displayName ?: Component.literal("null"))
                                .append(" / tabListDisplayName: ").append(p.tabListDisplayName ?: Component.literal("null"))
                        )
                    }
                    return@executes 1
                }
        )
    }
}