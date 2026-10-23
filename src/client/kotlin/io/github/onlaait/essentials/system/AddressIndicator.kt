package io.github.onlaait.essentials.system

import net.minecraft.client.multiplayer.resolver.ServerAddress
import net.minecraft.network.chat.Component

object AddressIndicator {

    lateinit var component: Component
        private set

    fun onConnect(address: ServerAddress) {
        val host = address.host
        val port = address.port
        component = Component.literal(if (port == 25565) host else "$host:$port")
            .withStyle {
                it.withBold(true)
            }
    }
}