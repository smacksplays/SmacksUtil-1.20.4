package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SBackpackOpenPacketHandler {
    private static final C2SBackpackOpenPacketHandler INSTANCE = new C2SBackpackOpenPacketHandler();

    public static C2SBackpackOpenPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2SBackpackOpenPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.player().isPresent()) {
                Player player = context.player().get();
                ItemStack stack;
                if (data.slot() == -1){
                    stack = CuriosApi.getCuriosHelper().findCurios(player, "back").get(0).stack();
                } else {
                    stack = player.containerMenu.slots.get(data.slot()).getItem();
                }

                if (stack != null && stack.getItem() instanceof AbstractBackpackItem item) {
                    player.openMenu(item.createScreenHandlerFactory(stack));
                }
            }
        });
    }
}
