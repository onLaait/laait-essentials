package com.github.onlaait.essentials.config

import com.github.onlaait.essentials.LaaitEssentials
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = LaaitEssentials.ID)
class ModConfig : ConfigData {

    var maxTextLength = 1000
    var maxParticleCount = 3000

    @ConfigEntry.Gui.CollapsibleObject
    var interpolation = Interpolation()
    class Interpolation {
        @ConfigEntry.BoundedDiscrete(min = 1L, max = 20L)
        var player = 2
        @ConfigEntry.BoundedDiscrete(min = 1L, max = 20L)
        var minecart = 3
    }

    @ConfigEntry.Gui.CollapsibleObject
    var customJumpingCooldown = CustomJumpingCooldown()
    class CustomJumpingCooldown {
        var jumpingCooldown = 4
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        var inputPacketTrick = InputPacketTrick.CALM
    }

    var swordDance = false

    @ConfigEntry.Gui.CollapsibleObject
    var blockWave = BlockWave()
    class BlockWave {
        var period = 0.4
        var amplitude = 2.0
    }

    var showUntranslatableSubtitles = true

    var rejectTransfer = false
}