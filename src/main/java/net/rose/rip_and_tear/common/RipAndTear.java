package net.rose.rip_and_tear.common;

import moriyashiine.strawberrylib.api.SLib;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.rose.rip_and_tear.common.entity.mob.StatueEntity;
import net.rose.rip_and_tear.common.init.*;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class RipAndTear implements ModInitializer {
    public static final String MOD_ID = "rip_and_tear";

    @Override
    public void onInitialize() {
        SLib.init(MOD_ID);

        ModItems.init();
        ModBlocks.init();
        ModBlockEntityTypes.init();
        ModToolMaterials.initialize();
        ModItemGroups.init();
        ModEntities.init();
        ModComponents.init();
        ModSoundEvents.init();
        ModParticleTypes.init();

        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (source.getAttacker() != null && source.getAttacker() instanceof PlayerEntity player) {
                player.sendMessage(Text.literal("Damaged ").append(entity.getName()).formatted(Formatting.YELLOW).append(" for ").append(Text.literal(String.valueOf(damageTaken)).formatted(Formatting.RED)), true);
            }
        });

        UseEntityCallback.EVENT.register((playerEntity, world, hand, entity, entityHitResult) -> {
            if (!(entity instanceof StatueEntity statue)) return ActionResult.PASS;
            var i = playerEntity.getInventory().getSelectedSlot();
            var increment = playerEntity.isSneaking() ? 5 : -5;
            if (i == 0) statue.forcedHeadYaw += increment;
            else if (i == 1) statue.forcedBodyYaw += increment;
            else if (i == 2) statue.forcedPitch += increment;
            else if (i == 3) statue.forcedLimbSwingAnimationProgress += increment / 100F;
            else if (i == 4) statue.forcedLimbSwingAmplitude += increment / 100f;
            else return ActionResult.SUCCESS;
            return ActionResult.PASS;
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
