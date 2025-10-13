package com.github.onlaait.essentials.command

import com.github.onlaait.essentials.command.suggestion.FriendSuggestionProvider
import com.github.onlaait.essentials.command.suggestion.PastPlayersSuggestionProvider
import com.github.onlaait.essentials.system.Friend
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text

object FriendCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            literal("lfriend")
                .then(
                    literal("list")
                        .executes { context ->
                            val str = buildString {
                                append("Friend List")
                                for ((uuid, name) in Friend.list) {
                                    appendLine()
                                    append("$name ($uuid)")
                                }
                            }
                            context.source.sendFeedback(Text.literal(str))
                            return@executes 1
                        }
                )
                .then(
                    literal("add")
                        .then(
                            argument("name", StringArgumentType.word())
                                .suggests(PastPlayersSuggestionProvider())
                                .executes { ctx ->
                                    val argName = StringArgumentType.getString(ctx, "name")
                                    run {
                                        for ((uuid, name) in Friend.playersCache) {
                                            if (name.equals(argName, ignoreCase = true)) {
                                                val added = Friend.add(uuid, name)
                                                if (added) {
                                                    ctx.source.sendFeedback(Text.literal("Add friend $name ($uuid)"))
                                                } else {
                                                    ctx.source.sendError(Text.literal("$name is already friend"))
                                                }
                                                return@run
                                            }
                                        }
                                        ctx.source.sendError(Text.translatable("argument.entity.notfound.player"))
                                    }
                                    return@executes 1
                                }
                        )
                )
                .then(
                    literal("remove")
                        .then(
                            argument("name", StringArgumentType.word())
                                .suggests(FriendSuggestionProvider())
                                .executes { ctx ->
                                    val name = StringArgumentType.getString(ctx, "name")
                                    val removed = Friend.remove(name)
                                    if (removed != null) {
                                        ctx.source.sendFeedback(Text.literal("Remove friend ${removed.second}"))
                                    } else {
                                        ctx.source.sendError(Text.literal("$name is not friend"))
                                    }
                                    return@executes 1
                                }
                        )
                )
        )
    }
}