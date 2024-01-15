package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.smackplays.smacksutil.items.AbstractBackpackItem;
import net.smackplays.smacksutil.items.LargeBackpackItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.platform.Services;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    public static final Item LIGHT_WAND = new LightWand(new Item.Properties().rarity(Rarity.EPIC).durability(200));
    public static final Item AUTO_LIGHT_WAND = new AutoLightWand(new Item.Properties().rarity(Rarity.EPIC).durability(2000));
    public static final Item MAGNET_ITEM = new MagnetItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item ADVANCED_MAGNET_ITEM = new AdvancedMagnetItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item MOB_TOOL_ITEM = new MobImprisonmentTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item ADV_MOB_TOOL_ITEM = new AdvancedMobImpTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item ENCH_TOOL = new FabricEnchantingTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final ResourceLocation ENCHANT_REQUEST_ID = new ResourceLocation(Constants.MOD_ID, "enchant-request");
    public static final ResourceLocation SORT_REQUEST_ID = new ResourceLocation(Constants.MOD_ID, "sort-request");

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        registerItem("backpack_item", BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        MenuScreens.register(SmacksUtil.GENERIC_9X6, BackpackScreen::new);

        registerItem("large_backpack_item", LARGE_BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        MenuScreens.register(SmacksUtil.GENERIC_13X9, LargeBackpackScreen::new);

        registerItem("enchanting_tool", ENCH_TOOL);
        MenuScreens.register(SmacksUtil.ENCHANTING_TOOL, EnchantingToolScreen::new);

        registerItem("light_wand", LIGHT_WAND);
        registerItem("auto_light_wand", AUTO_LIGHT_WAND);
        registerItem("magnet_item", MAGNET_ITEM);
        registerItem("advanced_magnet_item", ADVANCED_MAGNET_ITEM);
        registerItem("mob_imprisonment_tool", MOB_TOOL_ITEM);
        registerItem("advanced_mob_imp_tool", ADV_MOB_TOOL_ITEM);

        ServerPlayNetworking.registerGlobalReceiver(ENCHANT_REQUEST_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                AbstractContainerMenu screenHandler = player.containerMenu;
                ItemStack item = buf.readItem();
                screenHandler.slots.get(0).set(item);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(SORT_REQUEST_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                AbstractContainerMenu screenHandler = player.containerMenu;
                ItemStack stack = buf.readItem();
                if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu){
                    lBackpackMenu.sort();
                }
                else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu){
                    backpackMenu.sort();
                }
            });
        });
    }

    private void registerItem(String name, Item item){
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(item));
    }
}
