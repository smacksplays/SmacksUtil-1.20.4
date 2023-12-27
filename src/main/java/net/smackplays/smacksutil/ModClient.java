package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        Registry.register(Registries.ITEM, new Identifier(SmacksUtil.MOD_ID, "backpack_item"), BACKPACK_ITEM);
    }
}
