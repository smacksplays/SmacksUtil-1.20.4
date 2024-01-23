package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class C2SSetBlockAirPacketHandler {
    private static final C2SSetBlockAirPacketHandler INSTANCE = new C2SSetBlockAirPacketHandler();

    public static C2SSetBlockAirPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final C2SSetBlockAirPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.player().isPresent()){
                Level world = context.player().get().level();
                world.setBlockAndUpdate(data.pos(), Blocks.AIR.defaultBlockState());
            }
        });
    }
}
