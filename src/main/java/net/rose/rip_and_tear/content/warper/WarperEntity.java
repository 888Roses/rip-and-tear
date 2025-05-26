package net.rose.rip_and_tear.content.warper;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.rose.rip_and_tear.content.*;
import net.rose.rip_and_tear.util.Mathf;

public class WarperEntity extends PersistentProjectileEntity {
    public WarperEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        if (!(getWorld() instanceof ServerWorld serverWorld)) return;

        var victim = hitResult.getEntity();
        var owner = getOwner();

        if (owner == null || victim == null) return;
        var damageSource = serverWorld.getDamageSources().create(ModDamageTypes.WARPER_STING, this, owner);
        var couldDamageEntity = victim.damage(serverWorld, damageSource, 4F);

        if (couldDamageEntity) {
            var storedPosition = owner.getPos();
            var storedVelocity = owner.getVelocity();
            var ownerFacing = new Vec2f(owner.getYaw(), owner.getPitch());

            // Play teleportation sounds with random pitch between 0.75F and 1.25F.
            var soundPitch = Mathf.remap(getWorld().getRandom().nextFloat(), 0, 1, 0.75F, 1.25F);
            getWorld().playSoundFromEntity(
                    null,
                    owner,
                    ModSoundEvents.WARPER_WARP,
                    owner.getSoundCategory(),
                    1,
                    soundPitch
            );

            var emitter = GameEvent.Emitter.of(owner, owner.getSteppingBlockState());
            getWorld().emitGameEvent(GameEvent.TELEPORT, owner.getPos(), emitter);

            // Teleport owner to victim coordinates.
            // Currently, using requestTeleport doesn't make much sense since I believe this is supposed to be
            // called on the client to ask the server to teleport the victim.
            owner.requestTeleport(victim.getX(), victim.getY(), victim.getZ());
            owner.setVelocity(victim.getVelocity());
            owner.setYaw(victim.getYaw());
            owner.setPitch(victim.getPitch());

            // Teleport victim to previous owner coordinates.
            victim.setPosition(storedPosition);
            victim.setVelocity(storedVelocity);
            victim.setYaw(ownerFacing.x);
            victim.setPitch(ownerFacing.y);

            owner.getWorld().sendEntityStatus(owner, EntityStatuses.ADD_PORTAL_PARTICLES);
            if (owner instanceof PathAwareEntity pathAwareEntity) pathAwareEntity.getNavigation().stop();

            // Draw particles in a straight line between the two positions.
            final var INTERVAL = 1F;
            final var STEP = 0.5F;
            final var SPREAD = 0.1F;
            // The new owner position is the previous victim position.
            var strPos = owner.getPos().add(0, 0.5F, 0);
            var dstPos = victim.getPos().add(0, 0.5F, 0);
            var dist = dstPos.distanceTo(strPos);
            var dir = dstPos.subtract(strPos).normalize();

            for (var i = 0F; i < dist / INTERVAL; i += STEP) {
                var stepPos = strPos.add(dir.multiply(i));
                serverWorld.spawnParticles(
                        ModParticleTypes.CHARTER_SKULL,
                        stepPos.x,
                        stepPos.y,
                        stepPos.z,
                        1,
                        SPREAD,
                        SPREAD,
                        SPREAD,
                        0
                );
            }
        }
    }

    private boolean isProjectileStack(ItemStack stack) {
        if (stack.isEmpty() || !stack.isOf(ModItems.THROWN_WARPER)) return false;
        var uuid = stack.get(ModComponents.THROWN_WARPER_UUID);
        var possibleEntity = getWorld().getEntity(uuid);
        return possibleEntity == this;
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        for (var i = 0; i < player.getInventory().getMainStacks().size(); i++) {
            var slotStack = player.getInventory().getMainStacks().get(i);
            if (!isProjectileStack(slotStack)) continue;

            player.getInventory().setStack(i, new ItemStack(ModItems.WARPER));
            return true;
        }

        if (isProjectileStack(player.getOffHandStack())) {
            player.setStackInHand(Hand.OFF_HAND, new ItemStack(ModItems.WARPER));
            return true;
        }

        discard();
        return false;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.WARPER);
    }
}
