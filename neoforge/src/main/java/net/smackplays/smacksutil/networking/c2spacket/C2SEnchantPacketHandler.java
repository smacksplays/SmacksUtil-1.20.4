package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class C2SEnchantPacketHandler {
    private static final C2SEnchantPacketHandler INSTANCE = new C2SEnchantPacketHandler();

    public static C2SEnchantPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final C2SEnchantPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            ItemStack stack = data.item();
            if (context.player().isPresent()) {
                Player player = context.player().get();
                AbstractContainerMenu containerMenu = player.containerMenu;
                containerMenu.slots.get(0).set(stack);
            }
        });
    }
}
