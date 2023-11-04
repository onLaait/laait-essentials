package com.github.onlaait.essentials.mixin;

import com.github.onlaait.essentials.system.NoLaggyText;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Redirect(method = "getName()Lnet/minecraft/text/Text;", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text$Serializer;fromJson(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
    private MutableText injected(String json) {
        MutableText mutText = Text.Serializer.fromJson(json);
        if (mutText == null) return null;
        NoLaggyText.CensorResult censor = NoLaggyText.INSTANCE.censor(mutText);
        if (censor.getCensored()) {
            return (MutableText) censor.getText();
        } else {
            return mutText;
        }
    }
}