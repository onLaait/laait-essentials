package com.github.onlaait.essentials.system

import com.github.onlaait.essentials.LaaitEssentials
import com.github.onlaait.essentials.LaaitEssentials.logger

object NoLaggyParticle {

    fun apply(count: Int): Int {
        val maxCount = LaaitEssentials.config.maxParticleCount
        if (count > maxCount) {
            logger.warn("Mitigated a massive amount of particles: $count")
            return maxCount
        }
        return count
    }
}