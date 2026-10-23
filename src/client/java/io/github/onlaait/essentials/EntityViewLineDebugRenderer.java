package io.github.onlaait.essentials;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.debug.DebugValueAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityViewLineDebugRenderer implements DebugRenderer.SimpleDebugRenderer {
    final Minecraft minecraft;

    final double scale = LaaitEssentials.INSTANCE.getConfig().getViewLine().getLength();

    public EntityViewLineDebugRenderer(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @Override
    public void emitGizmos(double d, double e, double f, DebugValueAccess debugValueAccess, Frustum frustum, float g) {
        if (this.minecraft.level != null) {
            for (Entity entity : this.minecraft.level.entitiesForRendering()) {
                if (entity instanceof RemotePlayer &&
                        frustum.isVisible(entity.getBoundingBox())
                        && (entity != this.minecraft.getCameraEntity() || this.minecraft.options.getCameraType() != CameraType.FIRST_PERSON)) {
                    this.showViewLine(entity, g);
                }
            }
        }
    }

    private void showViewLine(Entity entity, float f) {
        Vec3 vec32 = entity.getPosition(f);

        Vec3 vec38 = vec32.add(0.0, entity.getEyeHeight(), 0.0);
        Vec3 vec39 = entity.getViewVector(f);
        Gizmos.line(vec38, vec38.add(vec39.scale(scale)), -65536);
    }
}
