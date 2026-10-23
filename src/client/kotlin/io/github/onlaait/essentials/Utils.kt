package io.github.onlaait.essentials

import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

object Utils {

    fun showChat(message: Component) {
        Minecraft.getInstance().execute {
            Minecraft.getInstance().player?.sendSystemMessage(message)
        }
    }

    fun parseArgs(input: String): Map<String, String> {
        if (input.isBlank()) return emptyMap()

        val regex = Regex("""--(\w+)\s*((?:(?!--).)*)""")
        return regex.findAll(input).associate { match ->
            match.groupValues[1] to match.groupValues[2].trim()
        }
    }

    fun setHandItem(itemStack: ItemStack) {
        val mc = Minecraft.getInstance()
        val player = mc.player!!
        val inv = player.inventory
        val slot = inv.selectedSlot
        inv.getSlot(slot)!!.set(itemStack)
        mc.gameMode!!.handleCreativeModeItemAdd(inv.getItem(slot), 36 + slot)
        player.inventoryMenu.broadcastChanges()
    }
}