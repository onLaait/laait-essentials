package com.github.onlaait.essentials.system

import com.github.onlaait.essentials.LaaitEssentials
import com.github.onlaait.essentials.mixin.KeyBindingAccessor
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.item.BlockItem
import kotlin.math.ceil
import kotlin.math.cos

object BlockWave {

    private var tick = 0
    private var delay = 0

    fun onPress() {
        val inv = MinecraftClient.getInstance().player!!.inventory
        if (inv.mainHandStack.item !is BlockItem) {
            var maxCount = 0
            var toSelect: Int? = null
            var i = -1
            for (stack in inv.main) {
                i++
                if (i > 8) break

                if (stack.item !is BlockItem) continue
                val count = stack.count
                if (count <= maxCount) continue
                maxCount = count
                toSelect = i
            }
            if (toSelect != null) inv.setSelectedSlot(toSelect)
            return
        }
        if (--delay <= 0) {
            val option = LaaitEssentials.config.blockWave
            delay = ceil((-cos(option.period * tick) + 1) * option.amplitude).toInt()
            KeyBinding.onKeyPressed((MinecraftClient.getInstance().options.useKey as KeyBindingAccessor).boundKey)
        }
        tick++
    }

    fun onNotPress() {
        tick = 0
        delay = 0
    }
}