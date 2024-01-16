package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.networking.SortData;

import java.util.Objects;

public class LargeBackpackScreen extends AbstractLargeBackpackScreen<LargeBackpackMenu> {
    public LargeBackpackScreen(LargeBackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void onButtonWidgetPressed() {
        SortData sortData = new SortData();
        if (this.minecraft != null) {
            Objects.requireNonNull(this.minecraft.getConnection()).send(sortData);
        }
    }
}