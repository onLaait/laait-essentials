package io.github.onlaait.essentials.system

import io.github.onlaait.essentials.LaaitEssentials
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.Style
import java.util.*

object NoLaggyText {

    private val AND_MORE = Component.literal("...(생략됨)")
        .withStyle(ChatFormatting.GRAY)
        .withStyle(ChatFormatting.ITALIC)

    var tick = 0
        private set

    fun exceededMaxLength(comp: Component): Boolean {
        var len = 0
        var exceeded = false
        comp.visit(FormattedText.ContentConsumer { str ->
            len += str.length
            if (len > LaaitEssentials.config.maxTextLength) {
                exceeded = true
                return@ContentConsumer FormattedText.STOP_ITERATION
            }
            return@ContentConsumer Optional.empty()
        })
        return exceeded
    }

    fun apply(text: Component): Component {
        val new = Component.empty()
        var len = 0
        text.visit(FormattedText.StyledContentConsumer { style, str ->
            val j = LaaitEssentials.config.maxTextLength - len
            if (j <= 0) {
                return@StyledContentConsumer FormattedText.STOP_ITERATION
            } else {
                new.append(Component.literal(if (str.length <= j) str else str.substring(0, j)).withStyle(style))
                len += str.length
                return@StyledContentConsumer Optional.empty()
            }
        }, Style.EMPTY)
        return new.append(AND_MORE)
    }

    init {
        ClientTickEvents.END_CLIENT_TICK.register {
            if (tick++ == 100000) tick = 0
        }
    }
}