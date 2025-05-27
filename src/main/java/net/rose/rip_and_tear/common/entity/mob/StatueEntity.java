package net.rose.rip_and_tear.common.entity.mob;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

public class StatueEntity extends MobEntity {
    public static final Object2BooleanMap<UUID> SLIM_STATUSES = new Object2BooleanOpenHashMap<>();

    public EntityPose forcedPose = EntityPose.STANDING;
    public float forcedHeadYaw = 0, forcedBodyYaw = 0, forcedPitch = 0, forcedLimbSwingAnimationProgress = 0, forcedLimbSwingAmplitude = 0;
    public int forcedClientAge = 0;

    public static final TrackedData<Boolean> SLIM = DataTracker.registerData(StatueEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public StatueEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        setPersistent();
        setBodyYaw(MathHelper.nextFloat(world.getRandom(), -90F, 90F));
        setHeadYaw(MathHelper.nextFloat(world.getRandom(), -90F, 90F));
        setPitch(MathHelper.nextFloat(world.getRandom(), -90F, 90F));

        this.forcedHeadYaw = this.headYaw;
        this.forcedBodyYaw = this.bodyYaw;
        this.forcedPitch = this.getPitch();
        this.forcedLimbSwingAnimationProgress = MathHelper.nextFloat(world.getRandom(), -1, 1);
        this.forcedLimbSwingAmplitude = MathHelper.nextFloat(world.getRandom(), -1, 1);
        this.forcedClientAge = this.age;
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient && SLIM_STATUSES.containsKey(getUuid())) {
            setSlim(SLIM_STATUSES.removeBoolean(getUuid()));
        }
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setSlim(nbt.getBoolean("Slim", false));
        forcedHeadYaw = nbt.getFloat("forced_head_yaw", 0);
        forcedBodyYaw = nbt.getFloat("forced_body_yaw", 0);
        forcedPitch = nbt.getFloat("forced_pitch", 0);
        forcedLimbSwingAnimationProgress = nbt.getFloat("forced_limb_swing_animation_progress", 0);
        forcedLimbSwingAmplitude = nbt.getFloat("forced_limb_swing_amplitude", 0);
        forcedClientAge = nbt.getInt("forced_client_age", 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Slim", isSlim());
        nbt.putFloat("forced_head_yaw", forcedHeadYaw);
        nbt.putFloat("forced_body_yaw", forcedBodyYaw);
        nbt.putFloat("forced_pitch", forcedPitch);
        nbt.putFloat("forced_limb_swing_animation_progress", forcedLimbSwingAnimationProgress);
        nbt.putFloat("forced_limb_swing_amplitude", forcedLimbSwingAmplitude);
        nbt.putInt("forcedClientAge", forcedClientAge);
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
}
