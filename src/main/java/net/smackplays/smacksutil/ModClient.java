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
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreen;
import net.smackplays.smacksutil.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SmacksUtil.MOD_ID, "backpack_item"), BACKPACK_ITEM);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(BACKPACK_ITEM));

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SmacksUtil.MOD_ID, "large_backpack_item"), LARGE_BACKPACK_ITEM);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(LARGE_BACKPACK_ITEM));

        MenuScreens.register(SmacksUtil.GENERIC_13X9, LargeBackpackScreen::new);

        ModConfig.init();
    }
}
