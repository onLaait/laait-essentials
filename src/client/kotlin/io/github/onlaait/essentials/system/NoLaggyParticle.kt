package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import io.github.onlaait.essentials.LaaitEssentials.LOGGER

object NoLaggyParticle {

    private val maxCount get() = LaaitEssentials.config.maxParticleCount

    fun apply(count: Int): Int {
        if (count > maxCount) {
            LOGGER.info("Mitigated a massive amount of particles: $count")
            return maxCount
        }
        return count
    }
}