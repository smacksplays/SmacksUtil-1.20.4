package net.smackplays.smacksutil.networking.S2CPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.smackplays.smacksutil.platform.Services;

public class PlayerBlockBreakPacketHandler {
    public static void handle(BlockPos pos) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        if (Services.KEY_HANDLER.isVeinKeyDown()){
            Services.VEIN_MINER.veinMiner(player.level(), player, pos);
        }
    }
}
