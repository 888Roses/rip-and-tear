package net.rose.rip_and_tear.common.entity.mob;

import com.google.common.collect.ImmutableMultimap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.rose.rip_and_tear.client.screen.StatueConfigurationScreen;
import net.rose.rip_and_tear.common.RipAndTear;
import net.rose.rip_and_tear.common.components.entity.StatueComponent;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.rose.rip_and_tear.common.util.SoundUtil;
import org.jetbrains.annotations.Nullable;

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

        this.getAttributes().addTemporaryModifiers(
                ImmutableMultimap.<RegistryEntry<EntityAttribute>, EntityAttributeModifier>builder()
                        .put(
                                EntityAttributes.SCALE,
                                new EntityAttributeModifier(
                                        RipAndTear.id("statue_scale"),
                                        0, EntityAttributeModifier.Operation.ADD_VALUE
                                )
                        )
                        .build()
        );
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

    // region Player interaction

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!player.isSneaking()) {
            var stackInHand = player.getStackInHand(hand);
            var currentStackInHand = this.getStackInHand(hand);
            if (!stackInHand.isEmpty()||!currentStackInHand.isEmpty()) {
                if (this.canPickUpLoot()) {
                    this.setStackInHand(hand, stackInHand);
                    player.setStackInHand(hand, currentStackInHand);

                    SoundUtil.playSound(
                            player.getWorld(), null, player.getPos(),
                            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(), player.getSoundCategory(),
                            1, 1
                    );

                    return ActionResult.SUCCESS;
                }
            }

            return ActionResult.PASS;
        }

        if (player instanceof ClientPlayerEntity) {
            MinecraftClient.getInstance().setScreen(new StatueConfigurationScreen(this));
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    // endregion

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

    // Audio feedback:
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    @Override
    public FallSounds getFallSounds() {
        return new FallSounds(SoundEvents.ENTITY_ARMOR_STAND_FALL, SoundEvents.ENTITY_ARMOR_STAND_FALL);
    }

    // endregion
}
