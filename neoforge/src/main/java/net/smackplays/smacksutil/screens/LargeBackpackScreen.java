package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.networking.SortData;

public class LargeBackpackScreen extends AbstractLargeBackpackScreen<LargeBackpackMenu> {
    public LargeBackpackScreen(LargeBackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void onButtonWidgetPressed() {
        ItemStack stack = this.menu.playerInventory.getSelected();
        SortData sortData = new SortData(stack);
        this.minecraft.getConnection().send(sortData);
    }
}