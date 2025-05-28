package net.rose.rip_and_tear.common.item;

import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.rose.rip_and_tear.common.init.ModEntityTypes;
import net.rose.rip_and_tear.common.util.SoundUtil;

public class StatueItem extends Item {
    public StatueItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var statue = ModEntityTypes.STATUE.create(context.getWorld(), SpawnReason.TRIGGERED);
        assert statue != null;
        statue.setPersistent();
        var pos = context.getHitPos();
        statue.requestTeleport(pos.x, pos.y, pos.z);
        ModEntityComponents.STATUE.get(statue).setValuesFromLivingEntity();
        context.getWorld().spawnEntity(statue);
        statue.lookAtEntity(context.getPlayer(), 1000, 1000);
        SoundUtil.playSound(
                context.getWorld(), null, pos,
                SoundEvents.BLOCK_STONE_PLACE, context.getPlayer().getSoundCategory(),
                1, 1
        );
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    ParticleTypes.CLOUD,
                    pos.x, pos.y + 0.1F, pos.z, 5,
                    0.25, 0, 0.25, 0.0025
            );
        }
        return ActionResult.CONSUME;
    }
}
