package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.BackpackMenu;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends AbstractBackpackItem {

    public BackpackItem() {
        super();
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new BackpackMenu(SmacksUtil.GENERIC_9X6.get(), syncId, playerInventory, new BackpackInventory(stack));
            }

            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}