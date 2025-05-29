package net.rose.rip_and_tear.common.entity.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FateCrusherEntity extends Entity {
    public static final int FATE_CRUSHER_LIFETIME = 8;
    public LivingEntity owner;
    public Vec3d pos;

    public FateCrusherEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age > 4 && getWorld() instanceof ServerWorld serverWorld) {
            for (var e : getWorld().getOtherEntities(this.owner, Box.from(pos).expand(3F), e1 -> e1 instanceof LivingEntity)) {
                e.damage(serverWorld, serverWorld.getDamageSources().magic(), 20);
                e.timeUntilRegen = 0;
            }
        }

        if (this.age > FATE_CRUSHER_LIFETIME) discard();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
