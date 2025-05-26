package net.rose.rip_and_tear.common.entity.projectile;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.rose.rip_and_tear.common.init.ModParticleTypes;
import net.rose.rip_and_tear.common.init.ModDamageTypes;
import net.rose.rip_and_tear.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.rose.rip_and_tear.common.init.ModComponents;
import net.rose.rip_and_tear.common.util.EntityUtil;
import net.rose.rip_and_tear.common.init.ModItems;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.rose.rip_and_tear.common.util.Mathf;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.world.event.GameEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.Hand;

public class WarperProjectileEntity extends PersistentProjectileEntity {
    public WarperProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.WARPER);
    }

    public DamageSource getDamageSource(ServerWorld serverWorld) {
        return serverWorld.getDamageSources().create(ModDamageTypes.WARPER_STING, this, this.getOwner());
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        if (!(getWorld() instanceof ServerWorld serverWorld)) return;

        var victim = hitResult.getEntity();
        var owner = getOwner();

        if (!SLibUtils.shouldHurt(owner, victim)) return;

        var damageSource = getDamageSource(serverWorld);
        var hasDamagedVictim = victim.damage(serverWorld, damageSource, WARPER_PROJECTILE_HIT_DAMAGE);
        if (!hasDamagedVictim) return;

        @SuppressWarnings("DataFlowIssue")
        var ownerStoredPosition = owner.getPos();
        var ownerStoredVelocity = owner.getVelocity();
        var ownerStoredRotation = EntityUtil.getRotation(owner);

        getWorld().playSoundFromEntity(
                null, owner,
                ModSoundEvents.WARPER_WARP, owner.getSoundCategory(),
                1, Mathf.random(
                        serverWorld,
                        WARPER_PROJECTILE_TELEPORT_SOUND_PITCH_MIN, WARPER_PROJECTILE_TELEPORT_SOUND_PITCH_MAX
                )
        );

        var emitter = GameEvent.Emitter.of(owner, owner.getSteppingBlockState());
        getWorld().emitGameEvent(GameEvent.TELEPORT, owner.getPos(), emitter);

        // TODO: Swap request teleport for something else, since request teleport is a RPC and we're the server.
        owner.requestTeleport(victim.getX(), victim.getY(), victim.getZ());
        owner.setVelocity(victim.getVelocity());
        owner.setYaw(victim.getYaw());
        owner.setPitch(victim.getPitch());

        victim.setPosition(ownerStoredPosition);
        victim.setVelocity(ownerStoredVelocity);
        EntityUtil.setRotation(victim, ownerStoredRotation);

        owner.getWorld().sendEntityStatus(owner, EntityStatuses.ADD_PORTAL_PARTICLES);
        if (owner instanceof PathAwareEntity pathAwareEntity) pathAwareEntity.getNavigation().stop();

        var startPosition = owner.getPos().add(0, 0.5F, 0);
        var destinationPosition = victim.getPos().add(0, 0.5F, 0);
        Mathf.inLine(
                startPosition, destinationPosition,
                THROWN_WARPER_RECALL_TRAIL_INTERVAL, THROWN_WARPER_RECALL_STEP_SIZE,
                stepPosition ->
                        serverWorld.spawnParticles(
                                ModParticleTypes.CHARTER_SKULL,
                                stepPosition.x, stepPosition.y, stepPosition.z, 1,
                                THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                                THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                                THROWN_WARPER_RECALL_PARTICLE_SPREAD,
                                0
                        )
        );
    }

    private boolean isProjectileStack(ItemStack stack) {
        if (stack.isEmpty() || !stack.isOf(ModItems.THROWN_WARPER)) return false;

        var uuid = stack.get(ModComponents.THROWN_WARPER_UUID);
        var possibleEntity = getWorld().getEntity(uuid);
        return possibleEntity == this;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        var inventory = player.getInventory();
        for (var i = 0; i < inventory.getMainStacks().size(); i++) {
            var stackInSlot = inventory.getMainStacks().get(i);
            if (!isProjectileStack(stackInSlot)) continue;
            inventory.setStack(i, new ItemStack(ModItems.WARPER));
            return true;
        }

        if (isProjectileStack(player.getOffHandStack())) {
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(ModItems.WARPER));
            return true;
        }

        return false;
    }
}
