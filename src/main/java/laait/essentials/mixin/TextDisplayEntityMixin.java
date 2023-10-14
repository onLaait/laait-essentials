package laait.essentials.mixin;

import laait.essentials.system.NoLaggyText;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayEntity.TextDisplayEntity.class)
public abstract class TextDisplayEntityMixin {
    @Shadow
    protected abstract Text getText();

    @Shadow
    protected abstract void setText(Text text);

    @Inject(method = "onTrackedDataSet(Lnet/minecraft/entity/data/TrackedData;)V", at = @At("TAIL"))
    private void injected(TrackedData<?> data, CallbackInfo ci) {
        if (data.getId() == 22) {
            Text text = this.getText();
            NoLaggyText.CensorResult censor = NoLaggyText.INSTANCE.censor(text);
            if (censor.getCensored()) this.setText(censor.getText());
        }
    }
}