package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.LargeBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;

public class C2SSortPacketHandler {
    private static final C2SSortPacketHandler INSTANCE = new C2SSortPacketHandler();

    public static C2SSortPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2SSortPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.player().isPresent()) {
                Player player = context.player().get();
                AbstractContainerMenu screenHandler = player.containerMenu;

                if (data.stack().getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
                    lBackpackMenu.sort();
                } else if (data.stack().getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
                    backpackMenu.sort();
                }
            }
        });
    }
}
