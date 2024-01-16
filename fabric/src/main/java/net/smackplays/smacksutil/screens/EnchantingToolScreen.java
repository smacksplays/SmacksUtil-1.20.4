package net.smackplays.smacksutil.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;


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