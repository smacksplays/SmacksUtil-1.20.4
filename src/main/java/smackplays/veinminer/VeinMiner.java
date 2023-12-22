package smackplays.veinminer;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smackplays.veinminer.events.PlayerBlockBreak;

public class VeinMiner implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("veinminer");
	public static Miner veinMiner;
    public static boolean fastPlace;


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());
		veinMiner = new Miner();
	}
	public static void toggleFastPlace() {
		fastPlace = !fastPlace;
	}
	public static boolean getFastPlace() {
		return fastPlace;
	}
}