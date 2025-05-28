package net.rose.rip_and_tear.common.item;

import static net.rose.rip_and_tear.common.init.ModConfiguration.*;

import net.rose.rip_and_tear.common.init.ModEntityComponents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.rose.rip_and_tear.common.util.RaycastUtils;
import net.rose.rip_and_tear.common.util.SoundUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.item.consume.UseAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;

public class SoulGluttonItem extends Item {
    public static LivingEntity clientLastTarget;

    public SoulGluttonItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user == null) return false;

        var target = getTarget(user);
        if (target == null) return false;

        ModEntityComponents.SOUL_STATE.maybeGet(target).ifPresent(component -> {
            if (user instanceof ClientPlayerEntity) clientLastTarget = target;

            if (component.getSoulIntegrity() <= 0) {
                component.setSoulIntegrity(SOUL_STATE_SOUL_INTEGRITY_MAXIMUM);

                SoundUtil.playSound(
                        user.getWorld(), null, user.getPos(),
                        SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, user.getSoundCategory(),
                        1, 1
                );

                if (user instanceof PlayerEntity player) {
                    player.getItemCooldownManager().set(stack, SOUL_GLUTTON_USE_COOLDOWN);
                    component.setDeadHeartCount(component.getDeadHeartCount() + 1);
                }
            }
        });

        return false;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user == null) return;

        var target = getTarget(user);
        if (target == null) return;

        ModEntityComponents.SOUL_STATE.maybeGet(target).ifPresent(component -> {
            // Both on client and server so let's see if it actually works or not lol
            component.decreaseSoulIntegrity(1);
            if (user instanceof ClientPlayerEntity) clientLastTarget = target;
        });

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    private LivingEntity getTarget(LivingEntity user) {
        var raycastEntity = user instanceof ClientPlayerEntity
                ? MinecraftClient.getInstance().getCameraEntity() : user;
        raycastEntity = raycastEntity == null ? user : raycastEntity;

        var raycastResult = RaycastUtils.raycastCrosshair(raycastEntity, SOUL_GLUTTON_INTERACTION_DISTANCE, 0);
        if (raycastResult.getType() != HitResult.Type.ENTITY) return null;

        return ((EntityHitResult) raycastResult).getEntity() instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return ActionResult.CONSUME;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}
