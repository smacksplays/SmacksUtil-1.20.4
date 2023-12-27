package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {

    public static final BackpackItem BACKPACK_ITEM = new BackpackItem();

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        Registry.register(Registries.ITEM, new Identifier(SmacksUtil.MOD_ID, "backpack_item"), BACKPACK_ITEM);
    }
}
