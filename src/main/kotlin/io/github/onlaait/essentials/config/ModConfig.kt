package io.github.onlaait.essentials.config

import io.github.onlaait.essentials.LaaitEssentials
import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = LaaitEssentials.MOD_ID)
class ModConfig : ConfigData {

    var maxTextLength = 2000
    var maxParticleCount = 3000

    @ConfigEntry.Gui.CollapsibleObject
    var interpolation = Interpolation()
    class Interpolation {
        @ConfigEntry.BoundedDiscrete(min = 1L, max = 20L)
        var player = 2
    }

/*    @ConfigEntry.Gui.CollapsibleObject
    var customJumpingCooldown = CustomJumpingCooldown()
    class CustomJumpingCooldown {
        var jumpingCooldown = 4
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        var inputPacketTrick = InputPacketTrick.CALM
    }*/

    @ConfigEntry.Gui.CollapsibleObject
    var blockWave = BlockWave()
    class BlockWave {
        var period = 0.4
        var amplitude = 2.0
    }

    @ConfigEntry.Gui.CollapsibleObject
    var viewLine = ViewLine()
    class ViewLine {
        var enabled = false
        var length = 5.0
    }

    var showUntranslatableSubtitles = true

    var rejectTransfer = false

    var hypixelApiKey = ""
}