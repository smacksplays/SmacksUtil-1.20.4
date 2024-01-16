package net.smackplays.smacksutil.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.services.IKeyHandler;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("unused")
public class NeoForgeKeyHandler extends IKeyHandler {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            veinKey = new KeyMapping(
                    KEY_SMACKSUTIL_VEINACTIVATE, InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL);
            veinPreviewKey = new KeyMapping(
                    KEY_SMACKSUTIL_VEINPREVIEW, InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL);
            fastPlaceKey = new KeyMapping(
                    KEY_SMACKSUTIL_FASTPLACE, InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL);
            exactMatchKey = new KeyMapping(
                    KEY_SMACKSUTIL_EXACTMATCH, InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_SMACKSUTIL);
            event.register(veinKey);
            event.register(veinPreviewKey);
            event.register(fastPlaceKey);
            event.register(exactMatchKey);
        }

    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            veinPreviewConsume();
            fastPlaceConsume();
            exactMatchConsume();
        }
    }
}
