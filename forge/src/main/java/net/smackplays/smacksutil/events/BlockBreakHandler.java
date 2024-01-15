package net.smackplays.smacksutil.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        }
    }
}
