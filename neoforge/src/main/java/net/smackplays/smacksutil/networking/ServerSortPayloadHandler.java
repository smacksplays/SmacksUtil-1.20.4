package net.smackplays.smacksutil.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;

public class ServerSortPayloadHandler {
    private static final ServerSortPayloadHandler INSTANCE = new ServerSortPayloadHandler();

    public static ServerSortPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final SortData data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    ItemStack stack = data.item();
                    Player player = context.player().get();
                    if (player != null){
                        AbstractContainerMenu containerMenu = player.containerMenu;
                        if (containerMenu instanceof BackpackMenu backpackMenu){
                            backpackMenu.sort();
                        } else if (containerMenu instanceof LargeBackpackMenu largeBackpackMenu) {
                            largeBackpackMenu.sort();
                        }
                    }
                });
    }
}
