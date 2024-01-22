package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.DyeableLeatherItem;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.AbstractBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractEnchantingToolScreen;
import net.smackplays.smacksutil.screens.AbstractLargeBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;

import static net.smackplays.smacksutil.SmacksUtil.*;

public class ModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        MenuScreens.register(BACKPACK_MENU, AbstractBackpackScreen<BackpackMenu>::new);
        MenuScreens.register(LARGE_BACKPACK_MENU, AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
        MenuScreens.register(ENCHANTING_TOOL_MENU, AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
        MenuScreens.register(TELEPORTATION_TABLET_MENU, AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);

        ClientPlayNetworking.registerGlobalReceiver(VEINMINER_SERVER_BLOCK_BREAK_REQUEST_ID, this::handleServerBlockBreak);
    }

    public void handleServerBlockBreak (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender){
        client.execute(() -> {
            if (Services.KEY_HANDLER.isVeinKeyDown()){
                Services.VEIN_MINER.veinMiner(client.level, client.player, buf.readBlockPos());
            }
        });
    }

}
