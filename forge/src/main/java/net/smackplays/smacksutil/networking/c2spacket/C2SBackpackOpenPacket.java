package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class C2SBackpackOpenPacket {
    private final int slot;

    public C2SBackpackOpenPacket(int s) {
        slot = s;
    }

    public C2SBackpackOpenPacket(FriendlyByteBuf buffer) {
        slot = buffer.readInt();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(slot);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        ItemStack stack = null;
        if (slot == -1){
            List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, "back");
            if (!results.isEmpty()){
                stack = results.get(0).stack();
            }
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }

        if (stack != null && stack.getItem() instanceof AbstractBackpackItem item) {
            player.openMenu(item.createScreenHandlerFactory(stack));
        }
    }
}