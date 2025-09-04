package com.github.onlaait.essentials.system

import net.minecraft.client.network.ServerAddress
import net.minecraft.text.Style
import net.minecraft.text.Text

object AddressIndicator {

    lateinit var text: Text
        private set

    fun onConnect(address: ServerAddress) {
        text = Text.literal("${address.address}:${address.port}").setStyle(Style.EMPTY.withBold(true))
    }
}