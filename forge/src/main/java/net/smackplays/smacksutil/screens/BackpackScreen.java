package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.networking.SSortPacket;


public class BackpackScreen extends AbstractBackpackScreen<BackpackMenu> {
    public BackpackScreen(BackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void onButtonWidgetPressed() {
        ItemStack stack = this.menu.playerInventory.getSelected();
        PacketHandler.sendToServer(new SSortPacket(stack));
    }
}