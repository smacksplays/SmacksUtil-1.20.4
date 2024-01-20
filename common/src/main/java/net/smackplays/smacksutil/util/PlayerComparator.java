package net.smackplays.smacksutil.util;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<BlockPos> {

    final Player player;

    public PlayerComparator(Player playerIn) {
        player = playerIn;
    }

    @Override
    public int compare(BlockPos o1, BlockPos o2) {
        if (player == null) return 0;
        double o1ToPlayer = o1.distToCenterSqr(player.blockPosition().getCenter());
        double o2ToPlayer = o2.distToCenterSqr(player.blockPosition().getCenter());
        if (o1ToPlayer < o2ToPlayer) return -1;
        else if (o1ToPlayer == o2ToPlayer) return 0;
        else if (o1ToPlayer > o2ToPlayer) return 1;
        else return 0;
    }
}
