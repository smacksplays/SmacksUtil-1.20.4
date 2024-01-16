package net.smackplays.smacksutil.config;

import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.smackplays.smacksutil.platform.NeoForgeModConfig;

public class ClothConfigNeoForge {
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) ->
                                NeoForgeModConfig.create().setTitle(Component.literal("SmacksUtil")).setParentScreen(parent).build()));
    }
}