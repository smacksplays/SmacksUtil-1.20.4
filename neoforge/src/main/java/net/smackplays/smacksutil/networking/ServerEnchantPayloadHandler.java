package net.smackplays.smacksutil.networking;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ServerEnchantPayloadHandler {
    private static final ServerEnchantPayloadHandler INSTANCE = new ServerEnchantPayloadHandler();

    public static ServerEnchantPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final EnchantData data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    ItemStack stack = data.item();
                    Player player = context.player().get();
                    if (player != null){
                        AbstractContainerMenu containerMenu = player.containerMenu;
                        containerMenu.slots.get(0).set(stack);
                    }
                });
    }
}
