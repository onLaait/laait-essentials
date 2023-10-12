package laait.essentials

import laait.essentials.system.NoLaggyText
import net.fabricmc.api.ClientModInitializer
import org.slf4j.LoggerFactory

object LaaitEssentials : ClientModInitializer {
    val logger = LoggerFactory.getLogger("laait-essentials")

	override fun onInitializeClient() {
		logger.info("Initializing Lqqit Essentials Mod")
		NoLaggyText
	}
}