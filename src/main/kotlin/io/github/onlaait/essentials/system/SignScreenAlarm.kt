package io.github.onlaait.essentials.system

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.nbt.NbtOps
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.ComponentSerialization
import net.minecraft.world.level.block.entity.SignText

object SignScreenAlarm {

    fun invoke(text: SignText) {
        val msgs = text.getMessages(false)
        val comp = Component.empty()
            .append(Component.literal("Edit sign: ").withStyle(ChatFormatting.GRAY))
        var i = 1
        msgs.forEach {
            val msgComp = NbtUtils.toPrettyComponent(
                ComponentSerialization.CODEC.encodeStart(NbtOps.INSTANCE, it).getOrThrow()
            )
            comp.append(msgComp)
            if (i++ != msgs.size) comp.append(Component.literal(" / ").withStyle(ChatFormatting.GRAY))
        }
        Minecraft.getInstance().player!!.displayClientMessage(comp, false)
    }
}