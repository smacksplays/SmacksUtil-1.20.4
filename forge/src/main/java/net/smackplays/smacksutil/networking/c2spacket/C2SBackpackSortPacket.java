package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;

public class C2SBackpackSortPacket {
    //private final ItemStack stack;

    public C2SBackpackSortPacket(ItemStack s) {
       //stack = s;
    }

    public C2SBackpackSortPacket(FriendlyByteBuf buffer) {
        //stack = buffer.readItem();
    }

    public void encode(FriendlyByteBuf buffer) {
        //buffer.writeItem(stack);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        AbstractContainerMenu screenHandler = player.containerMenu;

        /*if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
            lBackpackMenu.sort();
        } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
            backpackMenu.sort();
        }*/
    }
}