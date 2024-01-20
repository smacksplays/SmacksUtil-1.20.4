package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.LargeBackpackInventory;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import org.jetbrains.annotations.NotNull;

public class LargeBackpackItem extends AbstractBackpackItem {

    public LargeBackpackItem() {
        super();
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU.get(), syncId, playerInventory, new LargeBackpackInventory(stack));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }
}