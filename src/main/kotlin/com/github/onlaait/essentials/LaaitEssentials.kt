package com.github.onlaait.essentials

import com.github.onlaait.essentials.command.FriendCommand
import com.github.onlaait.essentials.config.ModConfig
import com.github.onlaait.essentials.system.Friend
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import org.slf4j.LoggerFactory


object LaaitEssentials : ClientModInitializer {

	const val ID = "laait-essentials"

	val logger = LoggerFactory.getLogger(ID)

	lateinit var config: ModConfig
		private set

	override fun onInitializeClient() {
		logger.info("Initializing Laait Essentials Mod")

		AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)
		config = AutoConfig.getConfigHolder(ModConfig::class.java).getConfig()

		Keys

        Friend.load()

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            FriendCommand.register(dispatcher)
        }
    }
}