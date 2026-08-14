package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import io.github.onlaait.essentials.LaaitEssentials.LOGGER

object NoLaggyParticle {

    fun apply(count: Int): Int {
        val maxCount = LaaitEssentials.config.maxParticleCount
        if (count > maxCount) {
            LOGGER.warn("Mitigated a massive amount of particles: $count")
            return maxCount
        }
        return count
    }
}