package net.smackplays.smacksutil.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smackplays.smacksutil.Constants;
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
    public static KeyMapping veinKey = new KeyMapping(
            KEY_SMACKSUTIL_VEINACTIVATE, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X, KEY_CATEGORY_SMACKSUTIL);
    public static KeyMapping veinPreviewKey = new KeyMapping(
            KEY_SMACKSUTIL_VEINPREVIEW, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y, KEY_CATEGORY_SMACKSUTIL);
    public static KeyMapping fastPlaceKey = new KeyMapping(
            KEY_SMACKSUTIL_FASTPLACE, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z, KEY_CATEGORY_SMACKSUTIL);
    public static KeyMapping exactMatchKey = new KeyMapping(
            KEY_SMACKSUTIL_EXACTMATCH, InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PERIOD, KEY_CATEGORY_SMACKSUTIL);

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
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
            if (veinPreviewKey.consumeClick()) {
                SmacksUtil.veinMiner.togglePreview();
                String str = SmacksUtil.veinMiner.renderPreview ? "Active" : "Inactive";
                int color = SmacksUtil.veinMiner.renderPreview ? GREEN : RED;
                Minecraft.getInstance().player.displayClientMessage(Component
                        .literal("Veinminer Preview: " + str).withColor(color), true);
            }
            if (fastPlaceKey.consumeClick()) {
                Services.CONFIG.setEnabledFastPlace(!Services.CONFIG.isEnabledFastPlace());
                String str = Services.CONFIG.isEnabledFastPlace() ? "Active" : "Inactive";
                int color = Services.CONFIG.isEnabledFastPlace() ? GREEN : RED;
                Minecraft.getInstance().player.displayClientMessage(Component
                        .literal("Fastplace: " + str).withColor(color), true);
            }
            if (exactMatchKey.consumeClick()) {
                SmacksUtil.veinMiner.toggleExactMatch();
                String str = SmacksUtil.veinMiner.isExactMatch() ? "Active" : "Inactive";
                int color = SmacksUtil.veinMiner.isExactMatch() ? GREEN : RED;
                Minecraft.getInstance().player.displayClientMessage(Component
                        .literal("Exact Match: " + str).withColor(color), true);
            }
        }
    }
}
