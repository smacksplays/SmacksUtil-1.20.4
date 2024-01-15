package net.smackplays.smacksutil.items;


import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;

import java.util.Comparator;

public abstract class AbstractBackpackItem extends Item implements DyeableLeatherItem {

    public AbstractBackpackItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isCrouching()) return InteractionResultHolder.pass(stack);
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResultHolder.pass(stack);
        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.CONSUME;
    }

    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new SimpleMenuProvider((i, playerInventory, playerEntity) ->
                new AbstractBackpackMenu(MenuType.GENERIC_9x6, i, playerInventory, new BackpackInventory(stack)), stack.getHoverName());
    }

    public void sortItems(ItemStack stack, String key) {
        CompoundTag tag = stack.getTagElement(key);
        ListTag listTag = tag.getList("Items", 10);
        NonNullList<ItemStack> items = NonNullList.withSize(listTag.size(), ItemStack.EMPTY);
        if (tag != null) {
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                int slot = compoundTag.getByte("Slot") & 255;
                if (slot >= 0 && slot < items.size()) {
                    items.set(slot, ItemStack.of(compoundTag));
                }
            }
        }
        items.sort(Comparator.comparing(this::getStringForSort));
        ContainerHelper.saveAllItems(tag, items);
    }

    private String getStringForSort(ItemStack stack) {
        String s = "";
        if (stack.isEmpty()) {
            return "zzz";
        } else if (stack.hasCustomHoverName()) {
            s = stack.getHoverName().getString();
        } else {
            s = stack.getDisplayName().getString();
        }
        return s;
    }
}