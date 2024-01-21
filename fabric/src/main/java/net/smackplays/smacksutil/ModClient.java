package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.DyeableLeatherItem;
import net.smackplays.smacksutil.platform.Services;

import static net.smackplays.smacksutil.SmacksUtil.*;
import net.smackplays.smacksutil.screens.*;

public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        MenuScreens.register(BACKPACK_MENU, BackpackScreen::new);
        MenuScreens.register(LARGE_BACKPACK_MENU, LargeBackpackScreen::new);
        MenuScreens.register(ENCHANTING_TOOL_MENU, EnchantingToolScreen::new);
        MenuScreens.register(TELEPORTATION_TABLET_MENU, TeleportationTabletScreen::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
    }
}
