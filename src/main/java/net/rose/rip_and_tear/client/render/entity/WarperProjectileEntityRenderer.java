package net.rose.rip_and_tear.client.render.entity;

import net.rose.rip_and_tear.common.entity.projectile.WarperProjectileEntity;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.util.Identifier;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

@Environment(EnvType.CLIENT)
public class WarperProjectileEntityRenderer extends
        ProjectileEntityRenderer<WarperProjectileEntity, ProjectileEntityRenderState> {
    public static final Identifier TEXTURE = RipAndTear.id("textures/entity/projectiles/warper.png");

    public WarperProjectileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    protected Identifier getTexture(ProjectileEntityRenderState arrowEntityRenderState) {
        return TEXTURE;
    }

    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }
}