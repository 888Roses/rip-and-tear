package net.rose.rip_and_tear.mixin.block;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.Block;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.rose.rip_and_tear.common.init.ModParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractCandleBlock.class)
public abstract class SoulCandleFlameMixin extends Block {
    public SoulCandleFlameMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author Rose
     * @reason Spawn blue flames when on top of deepslate related blocks.
     */
    @Overwrite
    private static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticleClient(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playSoundClient(
                        vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5,
                        SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS,
                        1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false
                );
            }
        }

        var particleType = ParticleTypes.SMALL_FLAME;
        var blockPos = BlockPos.ofFloored(vec3d);
        var state = world.getBlockState(blockPos.down());
        // TODO: Replace with a tag.
        var registryName = Registries.BLOCK.getId(state.getBlock()).getPath().toLowerCase();
        if (registryName.contains("soul") || registryName.contains("deepslate")) {
            particleType = ModParticleTypes.SMALL_SOUL_FLAME;
        }

        world.addParticleClient(particleType, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }
}