package io.github.onlaait.essentials.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import io.github.onlaait.essentials.Utils
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.network.Filterable
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.TooltipDisplay
import net.minecraft.world.item.component.WritableBookContent
import net.minecraft.world.item.component.WrittenBookContent
import net.minecraft.world.level.GameType

object BanbookCommand {

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            literal("banbook")
                .executes(::book)
                .then(
                    argument("arguments", StringArgumentType.greedyString())
                        .executes { ctx -> book(ctx, StringArgumentType.getString(ctx, "arguments")) }
                )
        )
    }

    private fun book(ctx: CommandContext<FabricClientCommandSource>, arguments: String? = null): Int {
        if (ctx.source.player.gameMode() != GameType.CREATIVE) return 0
        val book = Book()
        if (arguments != null) {
            val args = Utils.parseArgs(arguments)
            book.apply {
                args["length"]?.let { length = it.toInt() }
                args["page"]?.let { page = it.toInt() }
            }
        }
        Utils.setHandItem(book.make())
        return 1
    }

    private class Book {

        private companion object {
            val CHARS = '가'..'힣'
        }

        var length: Int = 10438
        var page: Int = 100

        fun make(): ItemStack {
            val _length = length
            var iter = CHARS.iterator()
            val pages = List(page) {
                Filterable.passThrough(Component.literal(
                    buildString {
                        repeat(_length) {
                            if (!iter.hasNext()) iter = CHARS.iterator()
                            append(iter.nextChar())
                        }
                    }
                ) as Component)
            }
            val cont = WrittenBookContent(Filterable.passThrough(""), "", 0, pages, true)
            val itemStack = ItemStack(Items.WRITTEN_BOOK, 1).apply {
                set(DataComponents.WRITTEN_BOOK_CONTENT, cont)
                set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false)
                set(DataComponents.ITEM_MODEL, ResourceLocation.withDefaultNamespace("charcoal"))
                set(DataComponents.ITEM_NAME, Component.translatable("item.minecraft.charcoal"))
                set(DataComponents.MAX_STACK_SIZE, 64)
                set(DataComponents.TOOLTIP_DISPLAY, TooltipDisplay.DEFAULT.withHidden(DataComponents.WRITTEN_BOOK_CONTENT, true))
            }

            return itemStack
        }

        fun make2(): ItemStack {
            val _length = length
            var iter = CHARS.iterator()
            val pages = List(page) {
                Filterable.passThrough(
                    buildString {
                        repeat(_length) {
                            if (!iter.hasNext()) iter = CHARS.iterator()
                            append(iter.nextChar())
                        }
                    }
                )
            }
            val cont = WritableBookContent(pages)
            val itemStack = ItemStack(Items.WRITABLE_BOOK, 1)
            itemStack.set(DataComponents.WRITABLE_BOOK_CONTENT, cont)

            return itemStack
        }
    }
}