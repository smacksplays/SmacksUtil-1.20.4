package net.smackplays.smacksutil.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;

import java.util.ArrayList;
import java.util.Map;


public class EnchantingToolScreen extends AbstractEnchantingToolScreen<EnchantingToolMenu> {
    public EnchantingToolScreen(EnchantingToolMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int $$2) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;

        Slot enchantSlot = this.menu.slots.get(0);

        ItemStack stack = enchantSlot.getItem().copy();
        if (!stack.isEmpty()) {
            ArrayList<Enchantment> list = new ArrayList<>();
            if (enchantSlot.hasItem()) {
                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                    int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
                    if (enchantment.category.canEnchant(stack.getItem()) && lvl == 0) list.add(enchantment);
                }
            }

            for (int i = 0; i < 6; ++i) {
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 124 >= mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    Enchantment enchantment = list.get(i);
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
                    map.putIfAbsent(enchantment, enchantment.getMaxLevel());
                    EnchantmentHelper.setEnchantments(map, stack);
                    enchantSlot.setChanged();
                    this.menu.setItem(0, 0, stack);
                    assert Minecraft.getInstance().gameMode != null;
                    Minecraft.getInstance().gameMode.handleSlotStateChanged(0, menu.containerId, false);

                    FriendlyByteBuf packet = PacketByteBufs.create();
                    packet.writeItem(stack);
                    ClientPlayNetworking.send(ModClient.ENCHANT_REQUEST_ID, packet);
                    return true;
                }
            }
            if (list.size() > 6 && insideScrollbar(x, y, mouseX, mouseY)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, $$2);
    }
}