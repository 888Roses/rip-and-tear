package net.rose.rip_and_tear.client.render.entity.mob;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

@Environment(EnvType.CLIENT)
public class StatueEntityRenderState extends BipedEntityRenderState {
    public boolean slim = false;
    public int textureIndex;
}