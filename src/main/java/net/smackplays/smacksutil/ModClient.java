package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.smackplays.smacksutil.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
