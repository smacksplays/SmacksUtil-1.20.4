package net.smackplays.smacksutil.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.networking.SEnchantPacket;

import java.util.ArrayList;
import java.util.Map;


public class EnchantingToolScreen extends AbstractEnchantingToolScreen<EnchantingToolMenu> {
    public EnchantingToolScreen(EnchantingToolMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }
    @Override
    public void sendPacket(ItemStack stack) {
        PacketHandler.sendToServer(new SEnchantPacket(stack));
    }
}