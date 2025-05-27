package net.rose.rip_and_tear.common.entity.mob;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.UUID;

public class StatueEntity extends MobEntity {
    public static final Object2BooleanMap<UUID> SLIM_STATUSES = new Object2BooleanOpenHashMap<>();


    public static final TrackedData<Boolean> SLIM = DataTracker.registerData(StatueEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);

    public StatueEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        setPersistent();

        setBodyYaw(MathHelper.nextFloat(world.getRandom(), -10F, 10F));
        setHeadYaw(MathHelper.nextFloat(world.getRandom(), -10F, 10F));
        setPitch(MathHelper.nextFloat(world.getRandom(), -10F, 10F));
        ModEntityComponents.STATUE.maybeGet(this).ifPresent(StatueComponent::setValuesFromLivingEntity);
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient && SLIM_STATUSES.containsKey(getUuid()))
            setSlim(SLIM_STATUSES.removeBoolean(getUuid()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setSlim(nbt.getBoolean("Slim", false));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Slim", isSlim());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SLIM, false);
    }

    public boolean isSlim() {
        return dataTracker.get(SLIM);
    }

    public void setSlim(boolean slim) {
        dataTracker.set(SLIM, slim);
    }

    // region Mob Definition

    // Not a mob and not a player, since is a statue.
    public boolean isMobOrPlayer() {
        return false;
    }

    // Cannot receive potion effects, obviously.
    public boolean isAffectedBySplashPotions() {
        return false;
    }

    // Cannot be pushed around.
    public boolean isPushable() {
        return false;
    }

    // endregion
}
