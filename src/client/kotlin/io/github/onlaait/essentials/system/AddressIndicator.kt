package io.github.onlaait.essentials.system

import net.minecraft.client.multiplayer.resolver.ServerAddress
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style

object AddressIndicator {

    lateinit var component: Component
        private set

    fun onConnect(address: ServerAddress) {
        component = Component.literal("${address.host}:${address.port}").setStyle(Style.EMPTY.withBold(true))
    }
}