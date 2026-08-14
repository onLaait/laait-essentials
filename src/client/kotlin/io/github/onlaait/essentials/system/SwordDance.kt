package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import io.github.onlaait.essentials.mixin.KeyMappingAccessor
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.phys.HitResult

object SwordDance {

    private val SWORD_IDS = arrayOf(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
        .map { Item.getId(it) }.toIntArray()

    private val noTargetRandomTicks = intArrayOf(2, 2, 2, 3)
    private val targetRandomTicks = intArrayOf(2, 3, 3, 3, 3, 3, 4)

    private val swordDanceKey = LaaitEssentials.keys.swordDance
    private val attackKey = Minecraft.getInstance().options.keyAttack
    private val useKey = Minecraft.getInstance().options.keyUse

    private var i = 0
    private var useI = 0

    private var virtuallyPressedAttackKey = false
    private var virtuallyPressedUseKey = false

    fun tick() {
        var pressedSwordDanceKey = false
        while (swordDanceKey.consumeClick()) {
            pressedSwordDanceKey = true
        }
        if (pressedSwordDanceKey) {
            i = 0
            useI = 0
            (attackKey as KeyMappingAccessor).setClickCount(1)
        }

        if (virtuallyPressedAttackKey) {
            attackKey.isDown = false
            virtuallyPressedAttackKey = false
        }
        if (virtuallyPressedUseKey) {
            useKey.isDown = false
            virtuallyPressedUseKey = false
        }
        if (swordDanceKey.isDown) {
            val targetType = Minecraft.getInstance().hitResult?.type
            if (Minecraft.getInstance().player!!.mainHandItem.item.isSword() && targetType != HitResult.Type.BLOCK) {
                if (i > 0) i--
                if (useI > 0) useI--
                if (targetType == HitResult.Type.ENTITY) {
                    useI = 6
                }
                val hasTarget = useI > 0
                if (i == 0) {
                    i = (if (hasTarget) targetRandomTicks else noTargetRandomTicks).random()
                    (attackKey as KeyMappingAccessor).setClickCount(1)
                }
                val shouldKeepPressing = i > 1
                if (shouldKeepPressing) {
                    attackKey.isDown = true
                    virtuallyPressedAttackKey = true
                    if (hasTarget) {
                        if (!useKey.isDown) (useKey as KeyMappingAccessor).setClickCount(1)
                        useKey.isDown = true
                        virtuallyPressedUseKey = true
                    }
                }
            } else {
                attackKey.isDown = true
                virtuallyPressedAttackKey = true
            }
        }
    }

    private fun Item.isSword(): Boolean = SWORD_IDS.contains(Item.getId(this))
}