package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.smackplays.smacksutil.VeinMiner.Miner;
import net.smackplays.smacksutil.events.VeinMiner.PlayerBlockBreak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmacksUtil implements ModInitializer {
    public static final String MOD_ID = "smacksutil";
    public static final Logger LOGGER = LoggerFactory.getLogger("veinminer");
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

        LOGGER.info("Hello Fabric world!");

        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());
        veinMiner = new Miner();
    }
}