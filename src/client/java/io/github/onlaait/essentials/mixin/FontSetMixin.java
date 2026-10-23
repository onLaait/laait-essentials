package io.github.onlaait.essentials.mixin;

import com.mojang.blaze3d.font.GlyphProvider;
import io.github.onlaait.essentials.system.NoLaggyText;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gui.font.FontOption;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Mixin(FontSet.class)
public class FontSetMixin {

    @Final
    @Shadow
    private Int2ObjectMap<IntList> glyphsByWidth;

    @Inject(method = "selectProviders", at = @At("RETURN"))
    private void injected(List<GlyphProvider.Conditional> providers, Set<FontOption> options, CallbackInfoReturnable<List<GlyphProvider>> cir) {
        for (IntList intList : this.glyphsByWidth.values()) {
            Collections.shuffle(intList);
        }
    }

    @Redirect(method = "getRandomGlyph", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"))
    private int injected(RandomSource instance, int size) {
        return NoLaggyText.INSTANCE.getTick() % size;
    }
}