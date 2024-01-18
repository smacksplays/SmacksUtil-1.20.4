package net.smackplays.smacksutil.inventories;

import com.google.common.collect.Multimap;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IBackpackInventory extends WorldlyContainer {
    static IBackpackInventory of(NonNullList<ItemStack> items) {
        return () -> items;
    }

    static IBackpackInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    NonNullList<ItemStack> getItems();

    @Override
    default int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        int[] result = new int[getItems().size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    default boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, Direction side) {
        return true;
    }

    @Override
    default boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction side) {
        return true;
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);

            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    default @NotNull ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    default @NotNull ItemStack removeItem(int slot, int count) {
        if (count > 64) count = 64;
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, count);

        if (!result.isEmpty()) {
            setChanged();
        }

        return result;
    }

    @Override
    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    @Override
    default void setItem(int slot, @NotNull ItemStack stack) {
        var i = getItems();
        getItems().set(slot, stack);
        i = getItems();
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    default void clearContent() {
        getItems().clear();
    }

    @Override
    default void setChanged() {
    }

    @Override
    default boolean stillValid(@NotNull Player player) {
        return true;
    }


    @Override
    default int getMaxStackSize() {
        int baseStackSize = 64;
        ArrayList<ItemStack> upgrades = new ArrayList<>(){{
            add(getItem(0));
            add(getItem(1));
            add(getItem(2));
            add(getItem(3));
        }};
        for (int i = 0; i < 4; i++){
            ItemStack upgrade = upgrades.get(i);
            if (!upgrade.isEmpty()){
                List<AttributeModifier> modifiers = upgrade.getAttributeModifiers(EquipmentSlot.MAINHAND)
                        .get(Services.PLATFORM.getBackpackUpgradeMultiplierAttribute()).stream().toList();
                baseStackSize *= (int) modifiers.get(0).getAmount();
            }
        }
        return baseStackSize;
    }

    default boolean checkRemoveUpgrade(int corrCount) {
        for (int i = 0; i < getItems().size(); i++){
            if (getItems().get(i).getCount() > corrCount){
                return false;
            }
        }
        return true;
    }


    default void loadAllItems(CompoundTag tag, NonNullList<ItemStack> items) {
        ListTag listTag = tag.getList("Items", 10);

        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag cTag = listTag.getCompound(i);
            int slot = cTag.getByte("Slot") & 255;
            if (slot >= 0 && slot < items.size()) {
                items.set(slot, stackOf(cTag));
            }
        }
    }

    default ItemStack stackOf(CompoundTag tag) {
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(tag.getString("id")));
        ItemStack stack = new ItemStack(item);
        stack.setCount((int) tag.getFloat("Count"));
        if (tag.contains("tag", 10)) {
            stack.setTag(tag.getCompound("tag").copy());
            stack.getItem().verifyTagAfterLoad(stack.getTag());
        }

        if (stack.getItem().canBeDepleted()) {
            stack.setDamageValue(stack.getDamageValue());
        }
        return stack;
    }

    default CompoundTag saveAllItems(CompoundTag tag, NonNullList<ItemStack> items, boolean bl) {
        ListTag listTag = new ListTag();

        for(int i = 0; i < items.size(); ++i) {
            ItemStack stack = items.get(i);
            if (!stack.isEmpty()) {
                CompoundTag cTag = new CompoundTag();
                cTag.putByte("Slot", (byte)i);
                saveStack(stack, cTag);
                listTag.add(cTag);
            }
        }

        if (!listTag.isEmpty() || bl) {
            tag.put("Items", listTag);
        }

        return tag;
    }

    default CompoundTag saveStack(ItemStack stack, CompoundTag tag) {
        ResourceLocation $$1 = BuiltInRegistries.ITEM.getKey(stack.getItem());
        tag.putString("id", $$1 == null ? "minecraft:air" : $$1.toString());
        tag.putFloat("Count", (float)stack.getCount());
        if (stack.getTag() != null) {
            tag.put("tag", stack.getTag().copy());
        }

        return tag;
    }
}