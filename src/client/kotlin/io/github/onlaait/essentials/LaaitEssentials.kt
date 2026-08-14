package io.github.onlaait.essentials

import io.github.onlaait.essentials.command.BanbookCommand
import io.github.onlaait.essentials.command.ListCommand
import io.github.onlaait.essentials.command.TransferCommand
import io.github.onlaait.essentials.config.ModConfig
import io.github.onlaait.essentials.system.Bedwars
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object LaaitEssentials : ClientModInitializer {

	const val MOD_ID = "laait-essentials"

	val LOGGER = LoggerFactory.getLogger(MOD_ID)

	lateinit var config: ModConfig
		private set

	lateinit var keys: Keys
		private set

	override fun onInitializeClient() {
		LOGGER.info("[Laait Essentials] Initializing")

		keys = Keys

		AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)
		config = AutoConfig.getConfigHolder(ModConfig::class.java).getConfig()

		ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
			BanbookCommand.register(dispatcher)
			ListCommand.register(dispatcher)
			TransferCommand.register(dispatcher)
		}

		ClientReceiveMessageEvents.GAME.register { message, _ ->
			Bedwars.onReceiveMessage(message)
		}
	}

	fun id(path: String): Identifier =
		Identifier.fromNamespaceAndPath(MOD_ID, path)
}
