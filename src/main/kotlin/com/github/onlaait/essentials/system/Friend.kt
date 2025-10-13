package com.github.onlaait.essentials.system

import com.github.onlaait.essentials.LaaitEssentials
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.text.Style
import net.minecraft.text.Text
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.readLines
import kotlin.io.path.writeLines

object Friend {

    private val path = FabricLoader.getInstance().configDir.resolve("${LaaitEssentials.ID}-friends.txt")

    val FRIEND_SUFFIX = Text.literal(" ★")
        .setStyle(Style.EMPTY
            .withColor(65535)
        )

    private val USERNAME_RGX = Regex("^[A-Za-z0-9_]{3,16}$")

    val list = mutableListOf<Pair<UUID, String>>()

    val playersCache = mutableMapOf<UUID, String>()

    fun load() {
        if (!path.exists()) return
        val lines = path.readLines()
        list.clear()
        for (s in lines) {
            val ss = s.split(',', limit = 2)
            val uuid = UUID.fromString(ss[0])
            val name = ss[1]
            list += uuid to name
        }
    }

    fun save() {
        path.writeLines(list.map { "${it.first},${it.second}" })
    }

    fun add(uuid: UUID, name: String): Boolean {
        if (isFriend(uuid)) return false
        list += uuid to name
        save()
        return true
    }

    fun remove(name: String): Pair<UUID, String>? {
        val iter = list.iterator()
        for (e in iter) {
            if (e.second.equals(name, ignoreCase = true)) {
                iter.remove()
                save()
                return e
            }
        }
        return null
    }

    fun isFriend(uuid: UUID): Boolean = list.any { it.first == uuid }

    fun cachePlayer(uuid: UUID, name: String) {
        if (USERNAME_RGX.matches(name)) playersCache[uuid] = name
    }
}