package net.rose.rip_and_tear.common.item;

import net.rose.rip_and_tear.common.entity.projectile.WarperProjectileEntity;
import static net.rose.rip_and_tear.common.init.ModConfiguration.*;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.rose.rip_and_tear.common.util.SoundUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.rose.rip_and_tear.common.util.Mathf;
import net.minecraft.server.world.ServerWorld;
import net.rose.rip_and_tear.common.init.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.sound.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.stat.Stats;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;

public class WarperItem extends Item {
    public WarperItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        SoundUtil.playSound(
                world, null, user.getPos(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
                0.5F, 1F
        );

        if (world instanceof ServerWorld serverWorld) {
            var warperProjectileEntity = ProjectileEntity.spawn(
                    new WarperProjectileEntity(ModEntities.WARPER, world), serverWorld, stack,
                    projectile -> {
                        projectile.setOwner(user);
                        projectile.setPos(user.getPos().getX(), user.getEyeY(), user.getPos().getZ());
                        //noinspection DataFlowIssue
                        projectile.setVelocity(
                                projectile.getOwner(),
                                projectile.getOwner().getPitch(), projectile.getOwner().getYaw(),
                                0.0F, 3F, 0F
                        );
                    }
            );

            var thrownWarperItem = new ItemStack(ModItems.THROWN_WARPER);
            thrownWarperItem.set(ModComponents.THROWN_WARPER_UUID, warperProjectileEntity.getUuid());
            user.setStackInHand(hand, thrownWarperItem);
            user.getItemCooldownManager().set(thrownWarperItem, 30);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!(attacker.getWorld() instanceof ServerWorld serverWorld)) return;

        SoundUtil.playSound(
                serverWorld, null, attacker.getPos(),
                ModSoundEvents.WARPER_SWING, attacker.getSoundCategory(),
                WARPER_SWING_SOUND_VOLUME, Mathf.random(serverWorld, 0.9F, 1.1F)
        );

        var dir = target.getPos().subtract(attacker.getPos()).normalize();
        var pos = target.getPos().add(0, 1.35F, 0).add(dir.multiply(-0.25F));
        serverWorld.spawnParticles(
                ModParticleTypes.CHARTER_HIT,
                pos.x, pos.y, pos.z, 9,
                WARPER_HIT_PARTICLES_SPREAD,
                WARPER_HIT_PARTICLES_SPREAD,
                WARPER_HIT_PARTICLES_SPREAD,
                0
        );
    }
}
