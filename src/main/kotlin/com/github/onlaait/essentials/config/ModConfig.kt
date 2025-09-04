package com.github.onlaait.essentials.config

import me.shedaniel.autoconfig.ConfigData
import me.shedaniel.autoconfig.annotation.Config
import me.shedaniel.autoconfig.annotation.ConfigEntry

@Config(name = "laait-essentials")
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

    var realTimeHitbox = false

    @ConfigEntry.Gui.CollapsibleObject
    var customJumpingCooldown = CustomJumpingCooldown()
    class CustomJumpingCooldown {
        var jumpingCooldown = 4
        @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
        var inputPacketTrick = InputPacketTrick.CALM
    }

    var swordDance = false

    var showUntranslatableSubtitles = true

    var rejectTransfer = false
}