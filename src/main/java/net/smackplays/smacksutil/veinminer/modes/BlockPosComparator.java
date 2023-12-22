package net.smackplays.smacksutil.veinminer.modes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.Comparator;

public class BlockPosComparator implements Comparator<BlockPos> {

    PlayerEntity player;

    public BlockPosComparator(PlayerEntity playerIn) {
        player = playerIn;
    }

    @Override
    public int compare(BlockPos o1, BlockPos o2) {
        if (player == null) return 0;
        double o1ToPlayer = o1.getSquaredDistance(player.getPos());
        double o2ToPlayer = o2.getSquaredDistance(player.getPos());
        if (o1ToPlayer < o2ToPlayer) return -1;
        else if (o1ToPlayer == o2ToPlayer) return 0;
        else if (o1ToPlayer > o2ToPlayer) return 1;
        else return 0;
    }
}
