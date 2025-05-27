package net.rose.rip_and_tear.common.components.entity;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.MathHelper;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.util.Mathf;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class StatueComponent implements AutoSyncedComponent {
    private final LivingEntity livingEntity;

    private EntityPose forcedPose = EntityPose.STANDING;
    private float forcedHeadYaw = 0;
    private float forcedBodyYaw = 0;
    private float forcedPitch = 0;
    private float forcedLimbSwingAnimationProgress = 0;
    private float forcedLimbSwingAmplitude = 0;
    private int forcedClientAge = 0;

    public StatueComponent(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        forcedPose = EntityPose.valueOf(tag.getString("forced_pose", EntityPose.STANDING.toString()));
        forcedHeadYaw = tag.getFloat("forced_head_yaw", 0);
        forcedBodyYaw = tag.getFloat("forced_body_yaw", 0);
        forcedPitch = tag.getFloat("forced_pitch", 0);
        forcedLimbSwingAnimationProgress = tag.getFloat("forced_limb_swing_animation_progress", 0);
        forcedLimbSwingAmplitude = tag.getFloat("forced_limb_amplitude", 0);
        forcedClientAge = tag.getInt("forced_client_age", 0);
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putString("forced_pose", forcedPose.toString());
        tag.putFloat("forced_head_yaw", forcedHeadYaw);
        tag.putFloat("forced_body_yaw", forcedBodyYaw);
        tag.putFloat("forced_pitch", forcedPitch);
        tag.putFloat("forced_limb_swing_animation_progress", forcedLimbSwingAnimationProgress);
        tag.putFloat("forced_limb_amplitude", forcedLimbSwingAmplitude);
        tag.putInt("forced_client_age", forcedClientAge);
    }

    public void sync() {
        ModEntityComponents.STATUE.sync(livingEntity);
    }

    public EntityPose getForcedPose() {
        return forcedPose;
    }

    public float getForcedHeadYaw() {
        return forcedHeadYaw;
    }

    public float getForcedBodyYaw() {
        return forcedBodyYaw;
    }

    public float getForcedPitch() {
        return forcedPitch;
    }

    public float getForcedLimbSwingAnimationProgress() {
        return forcedLimbSwingAnimationProgress;
    }

    public float getForcedLimbSwingAmplitude() {
        return forcedLimbSwingAmplitude;
    }

    public int getForcedClientAge() {
        return forcedClientAge;
    }

    public void setValuesFromLivingEntity() {
        forcedPose = livingEntity.getPose();
        forcedHeadYaw = livingEntity.getHeadYaw();
        forcedBodyYaw = livingEntity.getBodyYaw();
        forcedPitch = livingEntity.getPitch();
        forcedLimbSwingAnimationProgress = MathHelper.nextFloat(livingEntity.getRandom(), -0.15F, 0.15F);
        forcedLimbSwingAmplitude = MathHelper.nextFloat(livingEntity.getRandom(), -0.15F, 0.15F);
        forcedClientAge = livingEntity.age;
    }
}
