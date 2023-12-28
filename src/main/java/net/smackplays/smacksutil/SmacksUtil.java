package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreenHandler;
import net.smackplays.smacksutil.veinminer.Miner;
import net.smackplays.smacksutil.events.veinminer.PlayerBlockBreak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmacksUtil implements ModInitializer {
    public static final String MOD_ID = "smacksutil";
    public static final Logger LOGGER = LoggerFactory.getLogger("veinminer");
    public static Miner veinMiner;
    public static boolean fastPlace;
    public static final ScreenHandlerType<LargeBackpackScreenHandler> GENERIC_13X9 = new ExtendedScreenHandlerType<>(LargeBackpackScreenHandler::createGeneric13x9);

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

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(SmacksUtil.MOD_ID, "generic_13x9"), GENERIC_13X9);
    }
}