package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyText;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontFilterType;
import net.minecraft.client.font.FontStorage;
import net.minecraft.util.math.random.Random;
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

@Mixin(FontStorage.class)
public abstract class FontStorageMixin {

    @Final
    @Shadow
    private Int2ObjectMap<IntList> charactersByWidth;

    @Inject(method = "applyFilters(Ljava/util/List;Ljava/util/Set;)Ljava/util/List;", at = @At("RETURN"))
    void injected(List<Font.FontFilterPair> allFonts, Set<FontFilterType> activeFilters, CallbackInfoReturnable<List<Font>> cir) {
        for (IntList intList : this.charactersByWidth.values()) {
            Collections.shuffle(intList);
        }
    }


    @Redirect(method = "getObfuscatedBakedGlyph(Lnet/minecraft/client/font/Glyph;)Lnet/minecraft/client/font/BakedGlyph;", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"))
    private int injected(Random random, int i) {
        return NoLaggyText.INSTANCE.getInt() % i;
    }
}