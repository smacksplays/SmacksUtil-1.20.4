package net.smackplays.smacksutil.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.SmacksUtil;

public class BlockBreakHandler {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onBreakBlock(BlockEvent.BreakEvent event) {
            if (!KeyInputHandler.veinKey.isDown() || SmacksUtil.veinMiner.isDrawing() || SmacksUtil.veinMiner.isMining()) {
                SmacksUtil.veinMiner.isMining = false;
                return;
            }
            SmacksUtil.veinMiner.veinMiner(event.getPlayer().level(), event.getPlayer(), event.getPos());
        }
    }
}
