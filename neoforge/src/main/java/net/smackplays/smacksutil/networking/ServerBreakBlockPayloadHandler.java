package net.smackplays.smacksutil.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ServerBreakBlockPayloadHandler {
    private static final ServerBreakBlockPayloadHandler INSTANCE = new ServerBreakBlockPayloadHandler();

    public static ServerBreakBlockPayloadHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final BreakBlockData data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
        BlockPos pos = data.pos();
        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.level().isPresent()) {
                Level world = context.level().get();
                world.destroyBlock(pos, true);
                //world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        });
    }
}
