package net.smackplays.smacksutil.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.menus.BackpackMenu;


public class BackpackScreen extends AbstractBackpackScreen<BackpackMenu> {
    public BackpackScreen(BackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void onButtonWidgetPressed() {
        FriendlyByteBuf packet = PacketByteBufs.create();
        ItemStack stack = this.menu.playerInventory.getSelected();
        packet.writeItem(stack);
        if (ClientPlayNetworking.canSend(SmacksUtil.SORT_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.SORT_REQUEST_ID, packet);
        }
    }
}