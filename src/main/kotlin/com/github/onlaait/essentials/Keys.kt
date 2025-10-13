package com.github.onlaait.essentials

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object Keys {

    val speedBuild =
        KeyBindingHelper.registerKeyBinding(KeyBinding(
            "key.laait-essentials.blockWave",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.laait-essentials"
        ))
}