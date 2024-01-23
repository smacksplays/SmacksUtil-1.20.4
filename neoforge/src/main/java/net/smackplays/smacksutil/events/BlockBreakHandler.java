package net.smackplays.smacksutil.events;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.platform.services.IKeyHandler;

@SuppressWarnings("unused")
public class BlockBreakHandler {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event) {
            Services.S2C_PACKET_SENDER.sendToPlayerBlockBreakPacket((ServerPlayer) event.getPlayer(), event.getPos());
        }
    }
}
