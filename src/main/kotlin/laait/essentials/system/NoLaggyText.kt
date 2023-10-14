package laait.essentials.system

import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting

object NoLaggyText {

    val style: Style = Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)).withItalic(false)

    fun censor(text: Text): CensorResult {
        var length = 0
        var lag = 0
        for (c in text.getWithStyle(Style.EMPTY)) {
            val lengthToAdd = c.string.length
            length += lengthToAdd
            lag += if (c.style.isObfuscated) lengthToAdd * 50 else lengthToAdd
        }
        if (lag > 1000) return CensorResult(true, Text.literal("(매우 긴 문자열: $length)")
            .setStyle(style))
        return CensorResult(false, text)
    }

    data class CensorResult(val censored: Boolean, val text: Text)
}