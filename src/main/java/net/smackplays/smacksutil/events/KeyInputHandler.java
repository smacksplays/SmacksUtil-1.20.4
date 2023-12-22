package net.smackplays.smacksutil.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.smackplays.smacksutil.SmacksUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    public static final String KEY_SMACKSUTIL_EXACTMATCH = "key.smacksutil.exactmatch";
    public static KeyBinding veinKey;
    public static KeyBinding veinPreviewKey;
    public static KeyBinding fastPlaceKey;
    public static KeyBinding exactMatchKey;

    public static void register() {
        veinKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_VEINACTIVATE, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL
        ));
        veinPreviewKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_VEINPREVIEW, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL
        ));
        fastPlaceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_FASTPLACE, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL
        ));
        exactMatchKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_EXACTMATCH, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_SMACKSUTIL
        ));

        registerEvents();
    }
    private static final int GREEN = 65280;
    private static final int RED = 16711680;

    public static void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.world != null) {
                if (veinPreviewKey.wasPressed()) {
                    SmacksUtil.veinMiner.togglePreview();
                    String str = SmacksUtil.veinMiner.renderPreview ? "Active" : "Inactive";
                    int color = SmacksUtil.veinMiner.renderPreview ? GREEN : RED;
                    client.player.sendMessage(Text
                            .literal("Veinminer Preview: " + str).withColor(color), true);
                }
                if (fastPlaceKey.wasPressed()) {
                    SmacksUtil.toggleFastPlace();
                    String str = SmacksUtil.getFastPlace() ? "Active" : "Inactive";
                    int color = SmacksUtil.getFastPlace() ? GREEN : RED;
                    client.player.sendMessage(Text
                            .literal("Fastplace: " + str).withColor(color), true);
                }
                if (exactMatchKey.wasPressed()) {
                    SmacksUtil.veinMiner.toggleExactMatch();
                    String str = SmacksUtil.veinMiner.isExactMatch() ? "Active" : "Inactive";
                    int color = SmacksUtil.veinMiner.isExactMatch() ? GREEN : RED;
                    client.player.sendMessage(Text
                            .literal("Exact Match: " + str).withColor(color), true);
                }
            }
        });
    }

}
