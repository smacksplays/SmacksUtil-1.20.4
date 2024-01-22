package net.smackplays.smacksutil.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.Services;

@SuppressWarnings("unused")
public class BlockBreakHandler {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event) {
            Services.SERVER_PACKET_SENDER.sendToPlayerBlockBreakPacket((ServerPlayer) event.getPlayer(), event.getPos());
        }
    }
}
