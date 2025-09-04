package com.github.onlaait.essentials.system

import com.github.onlaait.essentials.LaaitEssentials
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.text.StringVisitable
import net.minecraft.text.Style
import net.minecraft.text.Text
import java.util.*

object NoLaggyText {

    var int = 0
        private set

    private val AND_MORE = Text.literal("...(생략됨)")
        .setStyle(Style.EMPTY
            .withColor(-8421505)
            .withItalic(true)
        )

    fun exceededMaxLength(text: Text): Boolean {
        var len = 0
        var exceeded = false
        text.visit(StringVisitable.Visitor { str ->
            len += str.length
            if (len > LaaitEssentials.config.maxTextLength) {
                exceeded = true
                return@Visitor StringVisitable.TERMINATE_VISIT
            }
            return@Visitor Optional.empty()
        })
        return exceeded
    }

    fun apply(text: Text): Text {
        val new = Text.empty()
        var len = 0
        text.visit(StringVisitable.StyledVisitor { style, str ->
            val j = LaaitEssentials.config.maxTextLength - len
            if (j <= 0) {
                return@StyledVisitor StringVisitable.TERMINATE_VISIT
            } else {
                new.append(Text.literal(if (str.length <= j) str else str.substring(0, j)).fillStyle(style))
                len += str.length
                return@StyledVisitor Optional.empty()
            }
        }, Style.EMPTY)
        return new.append(AND_MORE)
    }

    init {
        ClientTickEvents.END_CLIENT_TICK.register {
            int++
            if (int < 0) int = 0
        }
    }
}