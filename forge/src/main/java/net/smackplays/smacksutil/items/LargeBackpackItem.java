package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.inventories.LargeBackpackInventory;
import org.jetbrains.annotations.Nullable;

public class LargeBackpackItem extends AbstractBackpackItem {

    public LargeBackpackItem() {
        super();
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new LargeBackpackMenu(syncId, playerInventory, new LargeBackpackInventory(stack));
            }

            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}