package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.items.LightWandItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class C2SToggleLightWandItemPacket {
    private final int slot;

    public C2SToggleLightWandItemPacket(int s) {
        slot = s;
    }

    public C2SToggleLightWandItemPacket(FriendlyByteBuf buffer) {
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
            List<SlotResult> results = CuriosApi.getCuriosHelper().findCurios(player, "hands");
            if (!results.isEmpty()){
                stack = results.get(0).stack();
            }
        } else {
            stack = player.containerMenu.slots.get(slot).getItem();
        }

        if (stack != null && stack.getItem() instanceof AutoLightWandItem item) {
            item.toggle(stack, player);
        }
    }
}