package net.smackplays.smacksutil.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.smackplays.smacksutil.platform.services.IKeyHandler;
import org.lwjgl.glfw.GLFW;

public class FabricKeyHandler extends IKeyHandler {
    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.level != null) {
                veinPreviewConsume();
                fastPlaceConsume();
                exactMatchConsume();
            }
        });
    }

    @Override
    public void register() {
        veinKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_SMACKSUTIL_VEINACTIVATE, InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL
        ));
        veinPreviewKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_SMACKSUTIL_VEINPREVIEW, InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL
        ));
        fastPlaceKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_SMACKSUTIL_FASTPLACE, InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL
        ));
        exactMatchKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_SMACKSUTIL_EXACTMATCH, InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_SMACKSUTIL
        ));
        registerEvents();
    }
}
