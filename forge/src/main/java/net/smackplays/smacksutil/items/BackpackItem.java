package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.BackpackMenu;
import org.jetbrains.annotations.NotNull;

public class BackpackItem extends AbstractBackpackItem {

    public BackpackItem() {
        super();
    }

    @Override
    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new MenuProvider() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(stack));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return stack.getHoverName();
            }
        };
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return null;
    }
}