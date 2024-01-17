package net.smackplays.smacksutil.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.platform.services.IKeyHandler;

@SuppressWarnings("unused")
public class BlockBreakHandler {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event) {
            if (!IKeyHandler.veinKey.isDown() || Services.VEIN_MINER.isDrawing() || Services.VEIN_MINER.isMining()) {
                Services.VEIN_MINER.isMining = false;
                return;
            }
            Services.VEIN_MINER.veinMiner(event.getPlayer().level(), event.getPlayer(), event.getPos());
            event.setCanceled(true);
        }
    }
}
