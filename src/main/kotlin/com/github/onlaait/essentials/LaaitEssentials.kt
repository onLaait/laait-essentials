package com.github.onlaait.essentials

import com.github.onlaait.essentials.system.NoLaggyText
import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object LaaitEssentials : ClientModInitializer {
    val logger = LoggerFactory.getLogger("laait-essentials")

	override fun onInitializeClient() {
		logger.info("Initializing Laait Essentials Mod")
		NoLaggyText
	}
}