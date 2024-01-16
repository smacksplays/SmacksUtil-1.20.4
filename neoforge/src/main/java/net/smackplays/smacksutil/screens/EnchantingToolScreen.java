package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.networking.EnchantData;

import java.util.Objects;


public class EnchantingToolScreen extends AbstractEnchantingToolScreen<EnchantingToolMenu> {
    public EnchantingToolScreen(EnchantingToolMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    public void sendPacket(ItemStack stack) {
        EnchantData enchantData = new EnchantData(stack);
        if (this.minecraft != null) {
            Objects.requireNonNull(this.minecraft.getConnection()).send(enchantData);
        }
    }
}