package net.rose.rip_and_tear.content.warper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.rose.rip_and_tear.content.ModComponents;
import net.rose.rip_and_tear.content.ModEntities;
import net.rose.rip_and_tear.content.ModItems;
import net.rose.rip_and_tear.content.ModParticleTypes;

import java.util.Objects;

public class WarperItem extends Item {
    public WarperItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL, 0.5F, 1F);

        if (world instanceof ServerWorld serverWorld) {
            var w = ProjectileEntity.spawn(new WarperEntity(ModEntities.WARPER, world), serverWorld, itemStack,
                    projectile -> {
                projectile.setOwner(user);
                projectile.setPos(user.getPos().getX(), user.getEyeY(), user.getPos().getZ());
                projectile.setVelocity(projectile.getOwner(),
                        Objects.requireNonNull(projectile.getOwner()).getPitch(), projectile.getOwner().getYaw(),
                        0.0F, 3F, 0F);
            });

            var thrownWarperStack = new ItemStack(ModItems.THROWN_WARPER);
            thrownWarperStack.set(ModComponents.THROWN_WARPER_UUID, w.getUuid());
            user.setStackInHand(hand, thrownWarperStack);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (attacker.getWorld() instanceof ServerWorld serverWorld) {
            var dir = target.getPos().subtract(attacker.getPos()).normalize();
            var particlePos = target.getPos().add(0, 1.35F, 0).add(dir.multiply(-0.25F));
            final var SPREAD = 0.35F;
            serverWorld.spawnParticles(
                    ModParticleTypes.CHARTER_HIT,
                    particlePos.x, particlePos.y, particlePos.z,
                    9,
                    SPREAD, SPREAD, SPREAD,
                    0
            );
        }
    }
}
