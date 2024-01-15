package net.smackplays.smacksutil.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;


public class BackpackScreen extends AbstractBackpackScreen<BackpackMenu> {
    public BackpackScreen(BackpackMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }
    @Override
    public void onButtonWidgetPressed(){
        /*FriendlyByteBuf packet = PacketByteBufs.create();
        ItemStack stack = this.menu.playerInventory.getSelected();
        packet.writeItem(stack);
        ClientPlayNetworking.send(ModClient.SORT_REQUEST_ID, packet);*/
    }
}