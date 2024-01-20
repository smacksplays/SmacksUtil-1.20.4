package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.events.veinminer.PlayerBlockBreak;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;

import static net.smackplays.smacksutil.Constants.*;

public class SmacksUtil implements ModInitializer {
    public static final MenuType<BackpackMenu> BACKPACK_MENU = new ExtendedScreenHandlerType<>(BackpackMenu::createGeneric9x6);
    public static final MenuType<LargeBackpackMenu> LARGE_BACKPACK_MENU = new ExtendedScreenHandlerType<>(LargeBackpackMenu::createGeneric13x9);
    public static final MenuType<EnchantingToolMenu> ENCHANTING_TOOL_MENU = new ExtendedScreenHandlerType<>(EnchantingToolMenu::create);
    public static final MenuType<TeleportationTabletMenu> TELEPORTATION_TABLET_MENU = new ExtendedScreenHandlerType<>(TeleportationTabletMenu::create);

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_LARGE_BACKPACK_MENU), LARGE_BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_BACKPACK_MENU), BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_ENCHANTING_TOOL_MENU), ENCHANTING_TOOL_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_TELEPORTATION_TABLET_MENU), TELEPORTATION_TABLET_MENU);

        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }


}
