package net.rose.rip_and_tear.common.entity.effect;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class FateCrusherEntity extends Entity {
    public LivingEntity owner;
    public Vec3d slamPosition;

    public LivingEntity getOwner() {
        return owner;
    }

    public Vec3d getSlamPosition() {
        return slamPosition;
    }

    public FateCrusherEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    private boolean isTarget(Entity entity) {
        return entity instanceof LivingEntity;
    }

    private DamageSource getDamageSource(ServerWorld serverWorld, Entity owner) {
        return owner instanceof PlayerEntity player
                ? serverWorld.getDamageSources().playerAttack(player)
                : serverWorld.getDamageSources().magic();
    }

    @Override
    public void tick() {
        super.tick();

        // Kill if too old.
        if (this.age > FATE_CRUSHER_ENTITY_LIFETIME) {
            discard();
            return;
        }

        var world = getWorld();
        var owner = getOwner();
        if (owner == null) return;

        // Check if the animation matches with dealing damage + if we're on server side.
        if (this.age <= FATE_CRUSHER_ENTITY_DAMAGE_START_AGE || !(world instanceof ServerWorld serverWorld)) return;

        var detectionBox = Box.from(getSlamPosition()).expand(FATE_CRUSHER_ENTITY_AOD_BOX_SIZE);
        // For each target in the detection range that isn't this weapon's owner.
        for (var target : serverWorld.getOtherEntities(owner, detectionBox, this::isTarget)) {
            // Deal damage.
            target.damage(serverWorld, getDamageSource(serverWorld, owner), FATE_CRUSHER_ENTITY_DAMAGE_PER_TICK);
            // Reset the iframe time.
            target.timeUntilRegen = 0;
        }
    }

    // We don't want to be able to damage the entity.
    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    // Nothing to save or load.
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    // Nothing to save or load.
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {}

    // Nothing to save or load.
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {}
}
