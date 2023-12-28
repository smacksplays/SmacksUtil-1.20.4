package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreen;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreenHandler;
import net.smackplays.smacksutil.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        Registry.register(Registries.ITEM, new Identifier(SmacksUtil.MOD_ID, "backpack_item"), BACKPACK_ITEM);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem)BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(BACKPACK_ITEM, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(itemGroup -> itemGroup.add(BACKPACK_ITEM));

        Registry.register(Registries.ITEM, new Identifier(SmacksUtil.MOD_ID, "large_backpack_item"), LARGE_BACKPACK_ITEM);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableItem)LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronBehavior.CLEAN_DYEABLE_ITEM);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(itemGroup -> itemGroup.add(LARGE_BACKPACK_ITEM));

        HandledScreens.register(SmacksUtil.GENERIC_13X9, LargeBackpackScreen::new);
    }
}
