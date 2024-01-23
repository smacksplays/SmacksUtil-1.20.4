package net.smackplays.smacksutil.networking.S2CPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.platform.Services;

public class S2CBlockBreakPacketHandler {
    private static final S2CBlockBreakPacketHandler INSTANCE = new S2CBlockBreakPacketHandler();

    public static S2CBlockBreakPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final S2CBlockBreakPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.player().isPresent()){
                BlockPos pos = data.pos();
                Player player = context.player().get();
                if (Services.KEY_HANDLER.isVeinKeyDown()){
                    Services.VEIN_MINER.veinMiner(player.level(), player, pos);
                }
            }
        });
    }
}
