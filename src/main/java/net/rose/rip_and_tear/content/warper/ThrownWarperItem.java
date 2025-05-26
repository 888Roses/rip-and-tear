package net.rose.rip_and_tear.content.warper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.rose.rip_and_tear.content.ModComponents;
import net.rose.rip_and_tear.content.ModItems;

import java.util.UUID;

public class ThrownWarperItem extends Item {
    public ThrownWarperItem(Settings settings) {
        super(settings);
    }

    protected Entity getEntity(World world, UUID uuid) {
        return world instanceof ServerWorld serverWorld ? serverWorld.getEntity(uuid) : null;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var comp = stack.get(ModComponents.THROWN_WARPER_UUID);
        if (comp != null) {
            var ent = getEntity(world, comp);
            if (ent != null) {
                user.setStackInHand(hand, new ItemStack(ModItems.WARPER));
                ent.discard();
            }
        }

        return super.use(world, user, hand);
    }
}
