package io.github.onlaait.essentials

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping

object Keys {

    private val CATEGORY = KeyMapping.Category.register(LaaitEssentials.id("main"))

    val swordDance =
        KeyMappingHelper.registerKeyMapping(KeyMapping(
            "key.laait-essentials.swordDance",
            InputConstants.Type.MOUSE,
            InputConstants.UNKNOWN.value,
            CATEGORY
        ))

    val buildWave =
        KeyMappingHelper.registerKeyMapping(KeyMapping(
            "key.laait-essentials.blockWave",
            InputConstants.Type.MOUSE,
            InputConstants.UNKNOWN.value,
            CATEGORY
        ))
}