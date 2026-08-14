package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import io.github.onlaait.essentials.mixin.KeyMappingAccessor
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.world.item.BlockItem
import kotlin.math.ceil
import kotlin.math.cos

object BlockWave {

    private var tick = 0
    private var delay = 0

    private val key = LaaitEssentials.keys.buildWave

    fun tick() {
        if (!key.isDown) {
            tick = 0
            delay = 0
            return
        }
        val inv = Minecraft.getInstance().player!!.inventory
        if (inv.selectedItem.item !is BlockItem) {
            var maxCount = 0
            var toSelect: Int? = null
            var i = -1
            for (stack in inv.nonEquipmentItems) {
                if (++i == 9) break

                if (stack.item !is BlockItem) continue
                val count = stack.count
                if (count <= maxCount) continue
                maxCount = count
                toSelect = i
            }
            if (toSelect != null) inv.selectedSlot = toSelect
            return
        }
        if (--delay <= 0) {
            val option = LaaitEssentials.config.blockWave
            delay = ceil((-cos(option.period * tick) + 1) * option.amplitude).toInt()
            KeyMapping.click((Minecraft.getInstance().options.keyUse as KeyMappingAccessor).key)
        }
        tick++
    }
}