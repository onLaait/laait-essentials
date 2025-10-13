package com.github.onlaait.essentials.command.suggestion

import com.github.onlaait.essentials.system.Friend
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import java.util.concurrent.CompletableFuture

class PastPlayersSuggestionProvider : SuggestionProvider<FabricClientCommandSource> {

    override fun getSuggestions(
        context: CommandContext<FabricClientCommandSource>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        for (name in Friend.playersCache.values) {
            builder.suggest(name)
        }
        return builder.buildFuture()
    }
}