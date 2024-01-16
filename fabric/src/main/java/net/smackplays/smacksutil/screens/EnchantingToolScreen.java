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
    public void sendPacket(ItemStack stack) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        ClientPlayNetworking.send(ModClient.ENCHANT_REQUEST_ID, packet);
    }
}