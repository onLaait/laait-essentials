package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import kotlinx.serialization.json.*
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.PlayerInfo
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.world.scores.PlayerTeam
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

object Bedwars {

    private val apiKey get() = LaaitEssentials.config.hypixelApiKey.trim()

    private val client = HttpClient.newHttpClient()

    private var starting = false

    fun onReceiveMessage(message: Component) {
        if (message.string.trim() == "Protect your bed and destroy the enemy beds.") {
            starting = true
        }
    }

    fun onMovePlayer() {
        if (!starting) return
        if (apiKey.isNotBlank()) {
            val list = Minecraft.getInstance().player!!.connection.listedOnlinePlayers
            run {
                val map = mutableMapOf<PlayerTeam?, MutableList<PlayerInfo>>()
                for (e in list) {
                    val team = e.team
                    map.getOrPut(team) { mutableListOf() } += e
                }
                for ((k, v) in map) {
                    LaaitEssentials.LOGGER.info(if (k == null) {
                        "team - null"
                    } else {
                        "team - name: ${k.name} / displayName: ${k.displayName} / formattedDisplayName: ${k.formattedDisplayName} / playerPrefix: ${k.playerPrefix} / playerSuffix: ${k.playerSuffix}"
                    } + " / players: ${v.map { it.profile.run { "$name($id)" } }}")
                }
            }
            val teamUuids = mutableMapOf<String, MutableList<String>>()
            for (e in list) {
                val team = e.team ?: continue
                teamUuids.getOrPut(team.name.filter { it in 'a'..'z' || it in 'A'..'Z' }) { mutableListOf() } += e.profile.id.toString()
            }
            val teamWl = mutableMapOf<String, Double>()
            val levels = mutableListOf<Int>()
            CompletableFuture.runAsync {
                for ((team, uuids) in teamUuids) {
                    teamWl[team] = uuids.sumOf {
                        try {
                            getStats(it).run {
                                if (this == null) {
                                    1.0
                                } else {
                                    levels += level
                                    if (wins + losses <= 5) {
                                        1.0 / 3.0
                                    } else if (losses == 0) {
                                        wins.toDouble()
                                    } else {
                                        wins.toDouble() / losses
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            LaaitEssentials.LOGGER.error("Error occured while getting WL: ${e.stackTraceToString()}")
                            Minecraft.getInstance().player?.displayClientMessage(Component.literal("Cannot get WL").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false)
                            return@runAsync
                        }
                    }
                }
                Minecraft.getInstance().player?.run {
                    displayClientMessage(Component.literal("[${levels.min()}~${levels.max()}]"), false)
                    for ((team, wl) in teamWl) {
                        displayClientMessage(Component.literal("$team: ${String.format("%.2f", wl)}"), false)
                    }
                }
            }
        }
        starting = false
    }

    private fun getStats(uuid: String): Stats? {
        val req = HttpRequest.newBuilder()
            .uri(URI.create("https://api.hypixel.net/v2/player?uuid=$uuid"))
            .setHeader("API-Key", apiKey)
            .build()
        val res = client.send(req, HttpResponse.BodyHandlers.ofString())
        val str = res.body()

        val o = Json.parseToJsonElement(str).jsonObject
        require(o["success"]!!.jsonPrimitive.boolean) {
            if (o["cause"]!!.jsonPrimitive.content == "Invalid API key") {
                Minecraft.getInstance().player?.displayClientMessage(Component.literal("Invalid API key").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false)
            }
            str
        }
        val player = o["player"]!!
        return if (player is JsonNull) {
            null
        } else {
            val player = player.jsonObject
            val bedwars = player["stats"]!!
                .jsonObject["Bedwars"]!!
                .jsonObject
            Stats(
                level = player
                    .jsonObject["achievements"]
                    ?.jsonObject["bedwars_level"]
                    ?.jsonPrimitive?.int ?: 0,
                wins = bedwars["four_four_wins_bedwars"]?.jsonPrimitive?.int ?: 0,
                losses = bedwars["four_four_losses_bedwars"]?.jsonPrimitive?.int ?: 0
            )
        }
    }

    private data class Stats(
        val level: Int,
        val wins: Int,
        val losses: Int,
    )
}