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

import static net.rose.rip_and_tear.common.init.ModConfiguration.FATE_CRUSHER_ITEM_USE_COOLDOWN;

public class FateCrusherItem extends Item {
    public FateCrusherItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world instanceof ServerWorld serverWorld) {
            var result = user.raycast(1000, 0, false);
            if (result.getType() == HitResult.Type.BLOCK) {
                var hammerEntity = ModEntityTypes.FATE_CRUSHER.create(world, SpawnReason.TRIGGERED);
                if (hammerEntity == null) return super.use(world, user, hand);

                hammerEntity.setPos(result.getPos().x, result.getPos().y, result.getPos().z);
                hammerEntity.setYaw(user.getYaw());
                hammerEntity.setBodyYaw(user.getBodyYaw());

                hammerEntity.owner = user;
                hammerEntity.slamPosition = result.getPos();

                world.spawnEntity(hammerEntity);

                SoundUtil.playSound(
                        serverWorld, null, result.getPos(),
                        ModSoundEvents.FATE_CRUSHER_CRUSH, SoundCategory.PLAYERS,
                        1, Mathf.random(serverWorld, 0.9F, 1.1F)
                );

                user.getItemCooldownManager().set(user.getStackInHand(hand), FATE_CRUSHER_ITEM_USE_COOLDOWN);
            }
        }

        return super.use(world, user, hand);
    }
}
