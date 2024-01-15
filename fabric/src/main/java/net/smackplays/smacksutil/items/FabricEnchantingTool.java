package net.smackplays.smacksutil.items;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import net.smackplays.smacksutil.inventories.ImplementedInventory;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;

public class FabricEnchantingTool extends AbstractEnchantingTool{
    public FabricEnchantingTool(Properties $$0) {
        super($$0);
    }

    @Override
    MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            }

            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new EnchantingToolMenu(syncId, playerInventory, new EnchantmentToolInventory(stack));
            }
        };
    }
}
