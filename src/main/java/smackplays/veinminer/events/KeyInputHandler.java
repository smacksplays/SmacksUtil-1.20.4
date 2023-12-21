package smackplays.veinminer.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import smackplays.veinminer.VeinMiner;

public class KeyInputHandler {

    public static final String KEY_CATEGORY_VEINMINER = "key.category.veinminer";
    public static final String KEY_VEINMINER_ACTIVE = "key.veinminer.activate";
    public static final String KEY_VEINMINER_PREVIEW = "key.veinminer.preview";
    public static KeyBinding veinKey;
    public static KeyBinding previewKey;

    public static void register(){
        veinKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_VEINMINER_ACTIVE, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X, KEY_CATEGORY_VEINMINER
        ));
        previewKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_VEINMINER_PREVIEW, InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y, KEY_CATEGORY_VEINMINER
        ));

        registerEvents();
    }

    public static void registerEvents(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(client.player != null && client.world != null){
                if(!VeinMiner.veinMiner.getInitState()){
                    VeinMiner.veinMiner.initModes(client.world, client.player);
                }
                if(previewKey.wasPressed()){
                    VeinMiner.veinMiner.togglePreview();
                    String str = VeinMiner.veinMiner.renderPreview ? "Active" : "Inactive";
                    client.player.sendMessage(Text.literal("Preview: " + str), true);
                }
            }
        });
    }

}
