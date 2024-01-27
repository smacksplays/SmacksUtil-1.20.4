package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.services.IKeyHandler;

public class FabricKeyHandler implements IKeyHandler {
    public static KeyMapping veinKey;
    public static KeyMapping veinPreviewKey;
    public static KeyMapping fastPlaceKey;
    public static KeyMapping exactMatchKey;
    public static KeyMapping openBackpackKey;
    public static KeyMapping toggleMagnetKey;
    public static KeyMapping toggleLightWandKey;

    boolean veinKeyDown;

    public void registerEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.level != null) {
                veinPreviewConsume(veinPreviewKey, client.player);
                fastPlaceConsume(fastPlaceKey, client.player);
                exactMatchConsume(exactMatchKey, client.player);
                openBackpackConsume(openBackpackKey, client.player);
                toggleMagnetConsume(toggleMagnetKey, client.player);
                toggleLightWandConsume(toggleLightWandKey, client.player);
                veinKeyDown = veinKey.isDown();
            }
        });
    }
    @Override
    public void openBackpackConsume(KeyMapping key, Player player){
        if (key.consumeClick()) {
            NonNullList<Slot> slots = player.inventoryMenu.slots;
            for (int i = slots.size() - 1; i >= 0; i--){
                ItemStack stack = slots.get(i).getItem();
                if (stack.is(Services.PLATFORM.getLargeBackackItem()) || stack.is(Services.PLATFORM.getBackackItem())){
                    Services.C2S_PACKET_SENDER.BackpackOpenPacket(i);
                    return;
                }
            }
        }
    }

    @Override
    public boolean isVeinKeyDown() {
        return veinKeyDown;
    }

    @Override
    public void register() {
        veinKey = KeyBindingHelper.registerKeyBinding(IVeinKey);
        veinPreviewKey = KeyBindingHelper.registerKeyBinding(IVeinPreviewKey);
        fastPlaceKey = KeyBindingHelper.registerKeyBinding(IFastPlaceKey);
        exactMatchKey = KeyBindingHelper.registerKeyBinding(IExactMatchKey);
        openBackpackKey = KeyBindingHelper.registerKeyBinding(IOpenBackpackKey);
        toggleMagnetKey = KeyBindingHelper.registerKeyBinding(IToggleMagnetKey);
        toggleLightWandKey = KeyBindingHelper.registerKeyBinding(IToggleLightWandKey);
        registerEvents();
    }


}
