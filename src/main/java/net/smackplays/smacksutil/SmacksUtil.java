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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmacksUtil implements ModInitializer {
    public static final String MOD_ID = "smacksutil";
    public static final Logger LOGGER = LoggerFactory.getLogger("veinminer");
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

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(SmacksUtil.MOD_ID, "generic_13x9"), GENERIC_13X9);
    }
}