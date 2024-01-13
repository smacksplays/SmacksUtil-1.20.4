package net.smackplays.smacksutil.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.backpacks.ImplementedInventory;
import net.smackplays.smacksutil.slots.ItemManagerSlot;

import java.util.Optional;

public class FabricItemMonitorMenu extends AbstractContainerMenu {
    private final Container inventory;
    private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 3, 3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;
    private final Inventory playerInventory;
    private final int rows = 9;
    private final int cols = 11;

    public FabricItemMonitorMenu(int i, Inventory playerInv, Container inv, ContainerLevelAccess cAccess) {
        super(SmacksUtil.ITEM_MONITOR_MENU, i);
        this.inventory = inv;
        this.access = cAccess;
        this.player = playerInv.player;
        this.playerInventory = playerInv;
        inv.startOpen(playerInventory.player);
        int j;
        int k;

        int x_offset = 110;
        int y_offset = 135;
        this.addSlot(new ResultSlot(playerInv.player, this.craftSlots, this.resultSlots, 0, x_offset, y_offset));

        x_offset = -10;
        y_offset = 101;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 3; ++k) {
                this.addSlot(new Slot(craftSlots, k + j * 3, x_offset + 26 + k * 18, y_offset + 17 + j * 18));
            }
        }

        y_offset = -85;
        x_offset = -9;
        for (j = 0; j < 11; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new ItemManagerSlot(inv, k + j * 9, x_offset + 8 + k * 18, y_offset + j * 18));
            }
        }
        x_offset = -10;
        y_offset = 188;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, x_offset + 8 + k * 18, y_offset + j * 18));
            }
        }
        y_offset = 246;
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, x_offset + 8 + j * 18, y_offset));
        }


    }

    public static FabricItemMonitorMenu create(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new FabricItemMonitorMenu(syncId, playerInventory, ImplementedInventory.ofSize(11 * 9), ContainerLevelAccess.NULL);
    }

    protected static void slotChangedCraftingGrid(AbstractContainerMenu menu, Level world, Player player, CraftingContainer craftC, ResultContainer resultC) {
        if (!world.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ItemStack stack = ItemStack.EMPTY;
            Optional<RecipeHolder<CraftingRecipe>> oRecipe = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftC, world);
            if (oRecipe.isPresent()) {
                RecipeHolder<CraftingRecipe> recipeHolder = oRecipe.get();
                CraftingRecipe recipe = recipeHolder.value();
                if (resultC.setRecipeUsed(world, serverPlayer, recipeHolder)) {
                    ItemStack newStack = recipe.assemble(craftC, world.registryAccess());
                    if (newStack.isItemEnabled(world.enabledFeatures())) {
                        stack = newStack;
                    }
                }
            }

            resultC.setItem(0, stack);
            menu.setRemoteSlot(0, stack);
            serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, stack));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.access.execute((world, player1) -> itemStack2.getItem().onCraftedBy(itemStack2, world, player));
                if (!this.moveItemStackTo(itemStack2, this.rows * this.cols, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemStack2, itemStack);
            } else if (index < this.rows * this.cols) {
                if (!this.moveItemStackTo(itemStack2, this.rows * this.cols, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemStack2, 10, this.rows * this.cols, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemStack2);
            if (index == 0) {
                player.drop(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        this.access.execute((world, i) -> slotChangedCraftingGrid(this, world, this.player, this.craftSlots, this.resultSlots));
    }
}
