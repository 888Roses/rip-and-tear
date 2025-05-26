package net.rose.rip_and_tear.content.warper;

import net.rose.rip_and_tear.content.ModParticleTypes;
import net.rose.rip_and_tear.content.ModDamageTypes;
import net.rose.rip_and_tear.content.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.rose.rip_and_tear.content.ModItems;
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

public class ThrownWarperItem extends Item {
    public ThrownWarperItem(Settings settings) {
        super(settings);
    }

    protected WarperEntity getEntityFromWarperItem(ServerWorld serverWorld, ItemStack stack) {
        var uuid = stack.get(ModComponents.THROWN_WARPER_UUID);
        if (uuid == null) return null;
        return (WarperEntity) serverWorld.getEntity(uuid);
    }

    private void createTrail(ServerWorld serverWorld, WarperEntity warperEntity, PlayerEntity user) {
        final var INTERVAL = 1F;
        final var STEP = 0.5F;
        final var SPREAD = 0.1F;

        var startPos = warperEntity.getPos();
        var dstPos = user.getPos().add(0, 0.25F, 0);
        var dist = dstPos.distanceTo(startPos);
        var dir = dstPos.subtract(startPos).normalize();

        for (var i = 0F; i < dist / INTERVAL; i += STEP) {
            var stepPos = startPos.add(dir.multiply(i));
            var stepBlockPos = serverWorld.getTopPosition(
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.ofFloored(stepPos)
            );

            // region Spawn particles on the way.

            serverWorld.spawnParticles(
                    ModParticleTypes.CHARTER_SKULL,
                    stepPos.x,
                    stepBlockPos.getY() + 0.5F,
                    stepPos.z,
                    1,
                    SPREAD,
                    SPREAD,
                    SPREAD,
                    0
            );

            // endregion

            // region Hurt every entity around each position.

            var entityHurtBox = new Box(stepBlockPos).expand(1, 0, 1);
            var damageSource = serverWorld.getDamageSources().create(ModDamageTypes.WARPER_STING,
                    warperEntity, user);
            serverWorld.getEntitiesByType(
                    TypeFilter.instanceOf(Entity.class),
                    entityHurtBox,
                    x -> x.damage(serverWorld, damageSource, 4F)
            );

            // endregion
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        if (world instanceof ServerWorld serverWorld) {
            var warperEntity = getEntityFromWarperItem(serverWorld, stack);
            if (warperEntity == null) return ActionResult.PASS;

            // TODO: Handle enchantments, tags, etc.
            user.setStackInHand(hand, new ItemStack(ModItems.WARPER));

            createTrail(serverWorld, warperEntity, user);
            warperEntity.discard();
        }

        return super.use(world, user, hand);
    }
}