package net.smackplays.smacksutil.platform.services;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.items.LightWandItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.platform.Services;

import org.lwjgl.glfw.GLFW;

import static net.smackplays.smacksutil.Constants.*;

public interface IKeyHandler {

    KeyMapping IVeinKey = new KeyMapping(
            KEY_SMACKSUTIL_VEINACTIVATE, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IVeinPreviewKey = new KeyMapping(
            KEY_SMACKSUTIL_VEINPREVIEW, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IFastPlaceKey = new KeyMapping(
            KEY_SMACKSUTIL_FASTPLACE, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IExactMatchKey = new KeyMapping(
            KEY_SMACKSUTIL_EXACTMATCH, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IOpenBackpackKey = new KeyMapping(
            KEY_SMACKSUTIL_OPEN_BACKPACK, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IToggleMagnetKey = new KeyMapping(
            KEY_SMACKSUTIL_TOGGLE_MAGNET, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M, KEY_CATEGORY_SMACKSUTIL);
    KeyMapping IToggleLightWandKey = new KeyMapping(
            KEY_SMACKSUTIL_TOGGLE_LIGHT_WAND, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_N, KEY_CATEGORY_SMACKSUTIL);

    default void veinPreviewConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            Services.VEIN_MINER.togglePreview();
            String str = Services.VEIN_MINER.renderPreview ? "Active" : "Inactive";
            int color = Services.VEIN_MINER.renderPreview ? Constants.GREEN : Constants.RED;
            player.displayClientMessage(Component
                    .literal("Veinminer Preview: " + str).withColor(color), true);
            if (Services.CONFIG.isEnabledKeyPressSound()){
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    default void fastPlaceConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            Services.CONFIG.setEnabledFastPlace(!Services.CONFIG.isEnabledFastPlace());
            String str = Services.CONFIG.isEnabledFastPlace() ? "Active" : "Inactive";
            int color = Services.CONFIG.isEnabledFastPlace() ? Constants.GREEN : Constants.RED;
            player.displayClientMessage(Component
                    .literal("Fast Place: " + str).withColor(color), true);
            if (Services.CONFIG.isEnabledKeyPressSound()){
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    default void exactMatchConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            Services.VEIN_MINER.toggleExactMatch();
            String str = Services.VEIN_MINER.isExactMatch() ? "Active" : "Inactive";
            int color = Services.VEIN_MINER.isExactMatch() ? Constants.GREEN : Constants.RED;
            player.displayClientMessage(Component
                    .literal("Exact Match: " + str).withColor(color), true);
            if (Services.CONFIG.isEnabledKeyPressSound()){
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    default void toggleMagnetConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if (stack.is(Services.PLATFORM.getAdvancedMagnetItem())){
                    AdvancedMagnetItem item = (AdvancedMagnetItem) Services.PLATFORM.getAdvancedMagnetItem();
                    item.toggle(stack, player);
                    return;
                } else if (stack.is(Services.PLATFORM.getMagnetItem())){
                    MagnetItem item = (MagnetItem) Services.PLATFORM.getMagnetItem();
                    item.toggle(stack, player);
                    return;
                }
            }
        }
    }

    default void toggleLightWandConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if (stack.is(Services.PLATFORM.getAutoWandItem())){
                    AutoLightWandItem item = (AutoLightWandItem) Services.PLATFORM.getAutoWandItem();
                    item.toggle(stack, player);
                    return;
                }
            }
        }
    }

    void openBackpackConsume(KeyMapping key, Player player);

    boolean isVeinKeyDown();

    void register();
}
