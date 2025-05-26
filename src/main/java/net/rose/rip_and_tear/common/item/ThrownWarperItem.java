package net.rose.rip_and_tear.common.item;

import net.rose.rip_and_tear.common.entity.projectile.WarperProjectileEntity;
import static net.rose.rip_and_tear.common.init.ModConfiguration.*;
import net.rose.rip_and_tear.common.init.ModParticleTypes;
import net.rose.rip_and_tear.common.init.ModComponents;
import net.rose.rip_and_tear.common.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypeFilter;
import net.minecraft.world.Heightmap;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.util.Hand;
import net.minecraft.item.Item;
import net.rose.rip_and_tear.common.init.ModSoundEvents;
import net.rose.rip_and_tear.common.util.Mathf;
import net.rose.rip_and_tear.common.util.SoundUtil;

public class ThrownWarperItem extends Item {
    public ThrownWarperItem(Settings settings) {
        super(settings);
    }

    protected WarperProjectileEntity getEntityFromWarperItem(ServerWorld serverWorld, ItemStack stack) {
        var uuid = stack.get(ModComponents.THROWN_WARPER_UUID);
        if (uuid == null) return null;

        return (WarperProjectileEntity) serverWorld.getEntity(uuid);
    }

    protected void createTrail(ServerWorld serverWorld, WarperProjectileEntity warperProjectileEntity, PlayerEntity user) {
        var startingPosition = warperProjectileEntity.getPos();
        var destinationPosition = user.getPos().add(0, 0.25F, 0);
        var distance = destinationPosition.distanceTo(startingPosition);
        var direction = destinationPosition.subtract(startingPosition).normalize();

        for (var i = 0F; i < distance / THROWN_WARPER_RECALL_TRAIL_INTERVAL; i += THROWN_WARPER_RECALL_STEP_SIZE) {
            var stepPosition = startingPosition.add(direction.multiply(i));
            var stepBlockPosition = serverWorld.getTopPosition(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.ofFloored(stepPosition)
            );

            // region Spawn particles on the way.

            serverWorld.spawnParticles(
                    ModParticleTypes.CHARTER_SKULL,
                    stepPosition.x, stepBlockPosition.getY() + 0.5F, stepPosition.z, 1,
                    THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                    THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                    THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                    0
            );

            // endregion

            // region Hurt every entity around each position.

            var entityHurtBox = new Box(stepBlockPosition).expand(
                    THROWN_WARPER_RECALL_DAMAGE_RADIUS, 0,
                    THROWN_WARPER_RECALL_DAMAGE_RADIUS
            );
            var damageSource = warperProjectileEntity.getDamageSource(serverWorld);
            serverWorld.getEntitiesByType(
                    TypeFilter.instanceOf(Entity.class), entityHurtBox,
                    x -> {
                        var hasDamaged = x.damage(serverWorld, damageSource, THROWN_WARPER_RECALL_DAMAGE);

                        SoundUtil.playSound(
                                serverWorld, null, x.getPos(),
                                ModSoundEvents.WARPER_SWING, user.getSoundCategory(),
                                WARPER_SWING_SOUND_VOLUME, Mathf.random(serverWorld, 0.9F, 1.1F)
                        );

                        return hasDamaged;
                    }
            );

            // endregion
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            var warperProjectileEntity = getEntityFromWarperItem(serverWorld, stack);
            if (warperProjectileEntity == null) return ActionResult.PASS;

            // TODO: Handle enchantments, tags, etc.
            user.setStackInHand(hand, new ItemStack(ModItems.WARPER));

            createTrail(serverWorld, warperProjectileEntity, user);
            warperProjectileEntity.discard();
        }

        return super.use(world, user, hand);
    }
}