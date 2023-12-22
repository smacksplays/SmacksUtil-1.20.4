package net.smackplays.smacksutil.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import net.smackplays.smacksutil.SmacksUtil;

public class KeyInputHandler {

    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    public static KeyBinding veinKey;
    public static KeyBinding veinpreviewKey;
    public static KeyBinding fastplaceKey;

    public static void register(){
        veinKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_VEINACTIVATE, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL
        ));
        veinpreviewKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_VEINPREVIEW, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL
        ));
        fastplaceKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SMACKSUTIL_FASTPLACE, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL
        ));

        registerEvents();
    }

    public static void registerEvents(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null && client.world != null){
                if(veinpreviewKey.wasPressed()){
                    SmacksUtil.veinMiner.togglePreview();
                    String str = SmacksUtil.veinMiner.renderPreview ? "Active" : "Inactive";
                    client.player.sendMessage(Text.literal("Veinmienr Preview: " + str), true);
                }
                if(fastplaceKey.wasPressed()){
                    SmacksUtil.toggleFastPlace();
                    String str = SmacksUtil.getFastPlace() ? "Active" : "Inactive";
                    client.player.sendMessage(Text.literal("Fastplace: " + str), true);
                }
            }
        });
    }

}
