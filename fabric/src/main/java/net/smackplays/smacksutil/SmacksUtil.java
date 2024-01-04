package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreenHandler;
import net.smackplays.smacksutil.events.veinminer.PlayerBlockBreak;
import net.smackplays.smacksutil.veinminer.Miner;

public class SmacksUtil implements ModInitializer {

    public static final MenuType<LargeBackpackScreenHandler> GENERIC_13X9 = new ExtendedScreenHandlerType<>(LargeBackpackScreenHandler::createGeneric13x9);
    public static Miner veinMiner;
    public static boolean fastPlace;

    public static void toggleFastPlace() {
        fastPlace = !fastPlace;
    }

    public static boolean getFastPlace() {
        return fastPlace;
    }
    @Override
    public void onInitialize() {
        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());
        veinMiner = new Miner();

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(Constants.MOD_ID, "generic_13x9"), GENERIC_13X9);

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();
    }
}
