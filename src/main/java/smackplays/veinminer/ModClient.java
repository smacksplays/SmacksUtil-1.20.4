package smackplays.veinminer;

import net.fabricmc.api.ClientModInitializer;
import smackplays.veinminer.events.KeyInputHandler;

public class ModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}
