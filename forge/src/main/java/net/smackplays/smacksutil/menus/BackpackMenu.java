package net.smackplays.smacksutil.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;
//import top.theillusivec4.curios.api.CuriosApi;
//import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        ItemStack backpack = playerInventory.getSelected();
        if (!backpack.is(SmacksUtil.BACKPACK_ITEM.get())){
            if (Services.PLATFORM.isModLoaded("curios")){
                //List<SlotResult> slotResults = CuriosApi.getCuriosHelper().findCurios(playerInventory.player, "back");
               // if (!slotResults.isEmpty()){
                //    backpack = slotResults.get(0).stack();
               //     return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(backpack));
               // }
            }
            for (int i = playerInventory.getContainerSize(); i >= 0; i--){
                if (playerInventory.getItem(i).is(SmacksUtil.BACKPACK_ITEM.get())){
                    backpack = playerInventory.getItem(i);
                    break;
                }
            }
        }
        return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(backpack));
    }
}