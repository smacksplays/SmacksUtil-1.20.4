package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreenHandler;
import net.smackplays.smacksutil.event.veinminer.PlayerBlockBreak;
import net.smackplays.smacksutil.menus.FabricDiskReaderMenu;
import net.smackplays.smacksutil.menus.FabricItemMonitorMenu;

public class SmacksUtil implements ModInitializer {

    public static final MenuType<LargeBackpackScreenHandler> GENERIC_13X9 = new ExtendedScreenHandlerType<>(LargeBackpackScreenHandler::createGeneric13x9);
    public static final MenuType<FabricDiskReaderMenu> DISK_READER_MENU = new ExtendedScreenHandlerType<>(FabricDiskReaderMenu::create);
    public static final MenuType<FabricItemMonitorMenu> ITEM_MONITOR_MENU = new ExtendedScreenHandlerType<>(FabricItemMonitorMenu::create);

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "generic_13x9"), GENERIC_13X9);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "disk_reader_menu"), DISK_READER_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "item_monitor_menu"), ITEM_MONITOR_MENU);

        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
