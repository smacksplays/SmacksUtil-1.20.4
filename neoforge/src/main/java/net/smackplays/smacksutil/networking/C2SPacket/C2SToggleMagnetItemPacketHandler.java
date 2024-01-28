package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.MagnetItem;
import top.theillusivec4.curios.api.CuriosApi;

public class C2SToggleMagnetItemPacketHandler {
    private static final C2SToggleMagnetItemPacketHandler INSTANCE = new C2SToggleMagnetItemPacketHandler();

    public static C2SToggleMagnetItemPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2SToggleMagnetItemPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.player().isPresent()) {
                Player player = context.player().get();
                ItemStack stack;
                if (data.slot() == -1){
                    stack = CuriosApi.getCuriosHelper().findCurios(player, "charm").get(0).stack();
                } else {
                    stack = player.containerMenu.slots.get(data.slot()).getItem();
                }

                if (stack != null && stack.getItem() instanceof MagnetItem item) {
                    item.toggle(stack, player);
                } else if (stack != null && stack.getItem() instanceof AdvancedMagnetItem item){
                    item.toggle(stack, player);
                }
            }
        });
    }
}
