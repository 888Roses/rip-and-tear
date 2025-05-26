package net.rose.rip_and_tear.client.content;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;
import net.rose.rip_and_tear.RipAndTear;
import net.rose.rip_and_tear.content.warper.WarperEntity;

@Environment(EnvType.CLIENT)
public class WarperEntityRenderer extends ProjectileEntityRenderer<WarperEntity, ProjectileEntityRenderState> {
    public static final Identifier TEXTURE = Identifier.of(RipAndTear.MOD_ID, "textures/entity/projectiles/warper.png");

    public WarperEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    protected Identifier getTexture(ProjectileEntityRenderState arrowEntityRenderState) {
        return TEXTURE;
    }

    public ProjectileEntityRenderState createRenderState() {
        return new ProjectileEntityRenderState();
    }
}