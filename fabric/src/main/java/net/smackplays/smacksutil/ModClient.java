package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreen;
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

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "backpack_item"), BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(BACKPACK_ITEM));

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "large_backpack_item"), LARGE_BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(LARGE_BACKPACK_ITEM));
        MenuScreens.register(SmacksUtil.GENERIC_13X9, LargeBackpackScreen::new);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "light_wand"), LIGHT_WAND);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "auto_light_wand"), AUTO_LIGHT_WAND);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "magnet_item"), MAGNET_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "advanced_magnet_item"), ADVANCED_MAGNET_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "mob_imprisonment_tool"), MOB_TOOL_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "advanced_mob_imp_tool"), ADV_MOB_TOOL_ITEM);
    }
}
