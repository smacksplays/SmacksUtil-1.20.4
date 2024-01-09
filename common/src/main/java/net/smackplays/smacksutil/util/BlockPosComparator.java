package net.smackplays.smacksutil.util;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;

public class BlockPosComparator implements Comparator<BlockPos> {

    BlockPos sourcePos;

    public BlockPosComparator(BlockPos sourcePosIn) {
        sourcePos = sourcePosIn;
    }

    @Override
    public int compare(BlockPos o1, BlockPos o2) {
        if (sourcePos == null) return 0;
        double o1ToPlayer = o1.distToCenterSqr(sourcePos.getCenter());
        double o2ToPlayer = o2.distToCenterSqr(sourcePos.getCenter());
        if (o1ToPlayer < o2ToPlayer) return -1;
        else if (o1ToPlayer == o2ToPlayer) return 0;
        else if (o1ToPlayer > o2ToPlayer) return 1;
        else return 0;
    }
}
