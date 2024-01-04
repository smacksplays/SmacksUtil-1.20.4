package net.smackplays.smacksutil.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.Services;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    public static final String KEY_SMACKSUTIL_EXACTMATCH = "key.smacksutil.exactmatch";
    private static final int GREEN = 65280;
    private static final int RED = 16711680;
    public static KeyMapping veinKey;
    public static KeyMapping veinPreviewKey;
    public static KeyMapping fastPlaceKey;
    public static KeyMapping exactMatchKey;

    public static void register() {
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

    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.level != null) {
                if (veinPreviewKey.consumeClick()) {
                    SmacksUtil.veinMiner.togglePreview();
                    String str = SmacksUtil.veinMiner.renderPreview ? "Active" : "Inactive";
                    int color = SmacksUtil.veinMiner.renderPreview ? GREEN : RED;
                    client.player.displayClientMessage(Component
                            .literal("Veinminer Preview: " + str).withColor(color), true);
                }
                if (fastPlaceKey.consumeClick()) {
                    Services.CONFIG.setEnabledFastPlace(!Services.CONFIG.isEnabledFastPlace());
                    String str = Services.CONFIG.isEnabledFastPlace() ? "Active" : "Inactive";
                    int color = Services.CONFIG.isEnabledFastPlace() ? GREEN : RED;
                    client.player.displayClientMessage(Component
                            .literal("Fastplace: " + str).withColor(color), true);
                }
                if (exactMatchKey.consumeClick()) {
                    SmacksUtil.veinMiner.toggleExactMatch();
                    String str = SmacksUtil.veinMiner.isExactMatch() ? "Active" : "Inactive";
                    int color = SmacksUtil.veinMiner.isExactMatch() ? GREEN : RED;
                    client.player.displayClientMessage(Component
                            .literal("Exact Match: " + str).withColor(color), true);
                }
            }
        });
    }

}
