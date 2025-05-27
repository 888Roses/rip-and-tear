package net.rose.rip_and_tear.common.block.entity;

import net.rose.rip_and_tear.common.init.ModBlockEntityTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;

public class EngravedDeepslateBlockEntity extends BlockEntity {
    public static final int MAX_WIDTH = 7;
    public String line;

    public EngravedDeepslateBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ENGRAVED_DEEPSLATE, pos, state);
        this.line = getRandomString(MAX_WIDTH);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.line = nbt.getString("line", getRandomString(MAX_WIDTH));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putString("line", this.line);
    }

    private static String getRandomString(int width) {
        var builder = new StringBuilder(width);
        for (var i = 0; i < width; i++) builder.append(getRandomCharacter());
        return builder.toString();
    }

    private static char getRandomCharacter() {
        int rnd = (int) (Math.random() * 52); // or use Random or whatever
        char base = (rnd < 26) ? 'A' : 'a';
        return (char) (base + rnd % 26);
    }
}
