package io.github.onlaait.essentials

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import org.lwjgl.glfw.GLFW

object Keys {

    private val CATEGORY = KeyMapping.Category.register(LaaitEssentials.id("main"))

    val swordDance =
        KeyBindingHelper.registerKeyBinding(KeyMapping(
            "key.laait-essentials.swordDance",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_KEY_UNKNOWN,
            CATEGORY
        ))

    val buildWave =
        KeyBindingHelper.registerKeyBinding(KeyMapping(
            "key.laait-essentials.blockWave",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            CATEGORY
        ))
}