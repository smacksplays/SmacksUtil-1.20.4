package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;

import static net.smackplays.smacksutil.Constants.*;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    public static final Item LIGHT_WAND_ITEM = new LightWandItem();
    public static final Item AUTO_LIGHT_WAND_ITEM = new AutoLightWandItem();
    public static final Item MAGNET_ITEM = new MagnetItem();
    public static final Item ADVANCED_MAGNET_ITEM = new AdvancedMagnetItem();
    public static final Item MOB_CATCHER_ITEM = new MobCatcherItem();
    public static final Item ADVANCED_MOB_CATCHER_ITEM = new AdvancedMobCatcherItem();
    public static final Item ENCHANTING_TOOL_ITEM = new FabricEnchantingToolItem();
    public static final ResourceLocation ENCHANT_REQUEST_ID = new ResourceLocation(MOD_ID, "enchant-request");
    public static final ResourceLocation SORT_REQUEST_ID = new ResourceLocation(MOD_ID, "sort-request");
    public static final ResourceLocation BREAK_BLOCK_REQUEST_ID = new ResourceLocation(MOD_ID, "break-block-request");

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        registerItem(C_BACKPACK_ITEM, BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        MenuScreens.register(SmacksUtil.BACKPACK_SCREEN, BackpackScreen::new);

        registerItem(C_LARGE_BACKPACK_ITEM, LARGE_BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        MenuScreens.register(SmacksUtil.LARGE_BACKPACK_SCREEN, LargeBackpackScreen::new);

        registerItem(C_ENCHANTING_TOOL_ITEM, ENCHANTING_TOOL_ITEM);
        MenuScreens.register(SmacksUtil.ENCHANTING_TOOL_SCREEN, EnchantingToolScreen::new);

        registerItem(C_LIGHT_WAND_ITEM, LIGHT_WAND_ITEM);
        registerItem(C_AUTO_LIGHT_WAND_ITEM, AUTO_LIGHT_WAND_ITEM);
        registerItem(C_MAGNET_ITEM, MAGNET_ITEM);
        registerItem(C_ADVANCED_MAGNET_ITEM, ADVANCED_MAGNET_ITEM);
        registerItem(C_MOB_CATCHER_ITEM, MOB_CATCHER_ITEM);
        registerItem(C_ADVANCED_MOB_CATCHER_ITEM, ADVANCED_MOB_CATCHER_ITEM);

        ServerPlayNetworking.registerGlobalReceiver(ENCHANT_REQUEST_ID, (server, player, handler, buf, responseSender) -> server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            ItemStack item = buf.readItem();
            screenHandler.slots.get(0).set(item);
        }));

        ServerPlayNetworking.registerGlobalReceiver(SORT_REQUEST_ID, (server, player, handler, buf, responseSender) -> server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            ItemStack stack = buf.readItem();
            if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
                lBackpackMenu.sort();
            } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
                backpackMenu.sort();
            }
        }));

        ServerPlayNetworking.registerGlobalReceiver(BREAK_BLOCK_REQUEST_ID, (server, player, handler, buf, responseSender) -> server.execute(() -> {
            Level world = player.level();
            BlockPos pos = buf.readBlockPos();
            if (world.isClientSide) return;
            world.destroyBlock(pos, true);
        }));
    }

    private void registerItem(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(item));
    }
}
