package io.github.onlaait.essentials.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.onlaait.essentials.CustomPlayerRenderState;
import io.github.onlaait.essentials.LaaitEssentials;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "render(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;DDDLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/EntityRenderer;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"))
    private <S extends EntityRenderState> void injected(S entityRenderState, double d, double e, double f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, EntityRenderer<?, S> entityRenderer, CallbackInfo ci) {
        if (entityRenderState instanceof PlayerRenderState prs) {
            Vec3 vec3 = ((CustomPlayerRenderState) prs).laaitessentials$getViewFactor();
            if (vec3 != null) {
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.lines());
                double length = LaaitEssentials.INSTANCE.getConfig().getViewLine().getLength();
                ShapeRenderer.renderVector(poseStack, vertexConsumer, new Vector3f(0.0F, entityRenderState.eyeHeight, 0.0F), vec3.scale(length), -65536);
            }
        }
    }
}