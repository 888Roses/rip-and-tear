package net.rose.rip_and_tear.content.warper;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.rose.rip_and_tear.content.ModDamageTypes;
import net.rose.rip_and_tear.content.ModItems;

public class WarperEntity extends PersistentProjectileEntity {
    public WarperEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult res) {
        if (getWorld() instanceof ServerWorld serverWorld) {
            var entity = res.getEntity();
            var owner = getOwner();

            if (owner != null && entity != null && entity.damage(serverWorld, serverWorld.getDamageSources().create(ModDamageTypes.WARPER_STING, this, owner), 4F)) {
                var ownerPos = new Vec3d(owner.getX(), owner.getY(), owner.getZ());
                var ownerVel = new Vec3d(owner.getVelocity().getX(), owner.getVelocity().getY(), owner.getVelocity().getZ());

                if (owner instanceof PlayerEntity p) p.sendMessage(Text.literal("Hii ").append(owner.getName()), false);
                owner.setPosition(owner.getPos());
                owner.setVelocity(entity.getVelocity());
                entity.setPosition(ownerPos);
                entity.setVelocity(ownerVel);
            }
        }
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.WARPER);
    }
}
