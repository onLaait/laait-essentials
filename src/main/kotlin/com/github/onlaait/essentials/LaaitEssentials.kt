package com.github.onlaait.essentials

import com.github.onlaait.essentials.config.ModConfig
import me.shedaniel.autoconfig.AutoConfig
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer
import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object LaaitEssentials : ClientModInitializer {

	val logger = LoggerFactory.getLogger("laait-essentials")

	lateinit var config: ModConfig
		private set

	override fun onInitializeClient() {
		logger.info("Initializing Laait Essentials Mod")

		AutoConfig.register(ModConfig::class.java, ::Toml4jConfigSerializer)
		config = AutoConfig.getConfigHolder(ModConfig::class.java).getConfig()
	}
}