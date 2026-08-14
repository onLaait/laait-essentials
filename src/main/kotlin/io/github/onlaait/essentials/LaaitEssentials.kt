package io.github.onlaait.essentials

import io.github.onlaait.essentials.command.ListCommand
import io.github.onlaait.essentials.config.ModConfig
import io.github.onlaait.essentials.system.Bedwars
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import org.slf4j.LoggerFactory


object LaaitEssentials : ClientModInitializer {

	const val MOD_ID = "laait-essentials"

	val LOGGER = LoggerFactory.getLogger(MOD_ID)

	lateinit var config: ModConfig
		private set

	val keys = Keys

	override fun onInitializeClient() {
		LOGGER.info("[Laait Essentials] Initializing")

		AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)
		config = AutoConfig.getConfigHolder(ModConfig::class.java).getConfig()

        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			ListCommand.register(dispatcher)
        }

		ClientReceiveMessageEvents.GAME.register { message, _ ->
			Bedwars.onReceiveMessage(message)
		}
    }
}