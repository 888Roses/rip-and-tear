package net.rose.rip_and_tear.common.item;

import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.rose.rip_and_tear.common.init.ModEntityTypes;
import net.rose.rip_and_tear.common.init.ModSoundEvents;
import net.rose.rip_and_tear.common.util.Mathf;
import net.rose.rip_and_tear.common.util.SoundUtil;

public class FateCrusherItem extends Item {
    public FateCrusherItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld) {
            var raycast = user.raycast(1000, 0, false);
            if (raycast.getType() == HitResult.Type.BLOCK) {
                var entity = ModEntityTypes.FATE_CRUSHER.create(world, SpawnReason.TRIGGERED);
                entity.setPos(raycast.getPos().x, raycast.getPos().y, raycast.getPos().z);
                entity.setYaw(user.getYaw());
                entity.setBodyYaw(user.getBodyYaw());
                entity.owner = user;
                entity.pos = raycast.getPos();
                world.spawnEntity(entity);

                SoundUtil.playSound(
                        serverWorld, null, raycast.getPos(),
                        ModSoundEvents.FATE_CRUSHER_CRUSH, SoundCategory.PLAYERS,
                        1, Mathf.random(serverWorld, 0.9F, 1.1F)
                );
            }
        }

        return super.use(world, user, hand);
    }
}
