package net.rose.rip_and_tear.common.components.entity;

import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;

import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttribute;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.registry.entry.RegistryEntry;
import net.rose.rip_and_tear.common.RipAndTear;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.nbt.NbtCompound;

public class SoulStateComponent implements AutoSyncedComponent,
        CommonTickingComponent {

    private final LivingEntity livingEntity;
    private int deadHeartCount;

    private int soulIntegrityDecreaseAge;
    private int soulIntegrity;

    public SoulStateComponent(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        this.soulIntegrity = SOUL_STATE_SOUL_INTEGRITY_MAXIMUM;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        this.deadHeartCount = tag.getInt("dead_heart_count", 0);
        this.soulIntegrity = tag.getInt("soul_integrity", SOUL_STATE_SOUL_INTEGRITY_MAXIMUM);
        updateEntityAttributes();
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putInt("dead_heart_count", this.deadHeartCount);
        tag.putInt("soul_integrity", this.soulIntegrity);
    }

    public int getDeadHeartCount() {
        return deadHeartCount;
    }

    public void setDeadHeartCount(int deadHeartCount) {
        if (deadHeartCount > 10) deadHeartCount = 0;
        this.deadHeartCount = deadHeartCount;
        updateEntityAttributes();
        sync();
    }

    public static final Identifier MODIFIER_ID = RipAndTear.id("dead_heart_mod");

    private EntityAttributeModifier getModifier() {
        return new EntityAttributeModifier(MODIFIER_ID, -2 * deadHeartCount,
                EntityAttributeModifier.Operation.ADD_VALUE);
    }

    private ImmutableMultimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getAttributeMap() {
        return ImmutableMultimap.
                <RegistryEntry<EntityAttribute>, EntityAttributeModifier>builder()
                .put(EntityAttributes.MAX_HEALTH, getModifier())
                .build();
    }

    public void updateEntityAttributes() {
        if (livingEntity == null) return;

        var modifiers = getAttributeMap();
        if (livingEntity.getAttributes().hasModifierForAttribute(EntityAttributes.MAX_HEALTH, MODIFIER_ID))
            livingEntity.getAttributes().removeModifiers(modifiers);

        livingEntity.getAttributes().addTemporaryModifiers(modifiers);
    }

    public void sync() {
        ModEntityComponents.SOUL_STATE.sync(livingEntity);
    }

    @Override
    public void tick() {
        if (livingEntity.age - soulIntegrityDecreaseAge > SOUL_STATE_SOUL_INTEGRITY_REGAIN_COOLDOWN) {
            if (soulIntegrity < SOUL_STATE_SOUL_INTEGRITY_MAXIMUM) increaseSoulIntegrity(1);
        }
    }

    public int getSoulIntegrity() {
        return soulIntegrity;
    }

    public void setSoulIntegrity(int soulIntegrity) {
        soulIntegrity = Math.clamp(soulIntegrity, 0, SOUL_STATE_SOUL_INTEGRITY_MAXIMUM);

        if (soulIntegrity != this.soulIntegrity) {
            this.soulIntegrity = soulIntegrity;
            // TODO: Payload?
            sync();
        }
    }

    public void decreaseSoulIntegrity(int amount) {
        setSoulIntegrity(Math.max(0, this.soulIntegrity - amount));
        soulIntegrityDecreaseAge = livingEntity.age;
    }

    public void increaseSoulIntegrity(int amount) {
        setSoulIntegrity(Math.min(this.soulIntegrity + amount, SOUL_STATE_SOUL_INTEGRITY_MAXIMUM));
    }
}
