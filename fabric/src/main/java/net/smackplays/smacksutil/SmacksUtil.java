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

public class SmacksUtil implements ModInitializer {

    public static final MenuType<EnchantingToolMenu> ENCHANTING_TOOL = new ExtendedScreenHandlerType<>(EnchantingToolMenu::create);

    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "generic_13x9"), GENERIC_13X9);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "generic_9x6"), GENERIC_9X6);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "enchanting_tool"), ENCHANTING_TOOL);

        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }    public static final MenuType<BackpackMenu> GENERIC_9X6 = new ExtendedScreenHandlerType<>(BackpackMenu::createGeneric9x6);
    public static final MenuType<LargeBackpackMenu> GENERIC_13X9 = new ExtendedScreenHandlerType<>(LargeBackpackMenu::createGeneric13x9);


}
