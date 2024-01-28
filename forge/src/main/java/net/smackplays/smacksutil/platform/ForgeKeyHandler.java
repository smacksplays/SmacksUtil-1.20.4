package net.smackplays.smacksutil.platform;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.items.MagnetItem;
import net.smackplays.smacksutil.platform.services.IKeyHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Iterator;

@SuppressWarnings("unused")
public class ForgeKeyHandler implements IKeyHandler {
    public static KeyMapping veinKey;
    public static KeyMapping veinPreviewKey;
    public static KeyMapping fastPlaceKey;
    public static KeyMapping exactMatchKey;
    public static KeyMapping openBackpackKey;
    public static KeyMapping toggleMagnetKey;
    public static KeyMapping toggleLightWandKey;

    public static boolean veinKeyDown;

    @Override
    public void toggleMagnetConsume(KeyMapping key, Player player) {
        if (Services.PLATFORM.isModLoaded("curios")){
            Iterator<ICurioStacksHandler> slotsIterator = CuriosApi.getCuriosInventory(player).resolve().get().getUpdatingInventories().iterator();
            while (slotsIterator.hasNext()){
                ItemStack stack = slotsIterator.next().getStacks().getStackInSlot(0);
                if (stack.is(Services.PLATFORM.getMagnetItem())){
                    MagnetItem item = (MagnetItem) stack.getItem();
                    item.toggle(stack, player);
                    return;
                } else if (stack.is(Services.PLATFORM.getAdvancedMagnetItem())){
                    AdvancedMagnetItem item = (AdvancedMagnetItem) stack.getItem();
                    item.toggle(stack, player);
                }
            }
        }
        IKeyHandler.super.toggleMagnetConsume(key, player);
    }

    @Override
    public void toggleLightWandConsume(KeyMapping key, Player player) {
        if (Services.PLATFORM.isModLoaded("curios")){
            Iterator<ICurioStacksHandler> slotsIterator = CuriosApi.getCuriosInventory(player).resolve().get().getUpdatingInventories().iterator();
            while (slotsIterator.hasNext()){
                ItemStack stack = slotsIterator.next().getStacks().getStackInSlot(0);
                if (stack.is(Services.PLATFORM.getAutoWandItem())){
                    AutoLightWandItem item = (AutoLightWandItem) stack.getItem();
                    item.toggle(stack, player);
                    return;
                }
            }
        }
        IKeyHandler.super.toggleLightWandConsume(key, player);
    }

    @Override
    public void openBackpackConsume(KeyMapping key, Player player) {
        if (key.consumeClick()) {
            if (Services.PLATFORM.isModLoaded("curios")){
                Iterator<ICurioStacksHandler> slotsIterator = CuriosApi.getCuriosInventory(player).resolve().get().getUpdatingInventories().iterator();
                while (slotsIterator.hasNext()){
                    ItemStack stack = slotsIterator.next().getStacks().getStackInSlot(0);
                    if (stack.is(Services.PLATFORM.getLargeBackackItem()) || stack.is(Services.PLATFORM.getBackackItem())){
                        Services.C2S_PACKET_SENDER.BackpackOpenPacket(-1);
                        return;
                    }
                }
            }
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
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            veinKey = IVeinKey;
            veinPreviewKey = IVeinPreviewKey;
            fastPlaceKey = IFastPlaceKey;
            exactMatchKey = IExactMatchKey;
            openBackpackKey = IOpenBackpackKey;
            toggleMagnetKey = IToggleMagnetKey;
            toggleLightWandKey = IToggleLightWandKey;


            event.register(veinKey);
            event.register(veinPreviewKey);
            event.register(fastPlaceKey);
            event.register(exactMatchKey);
            event.register(openBackpackKey);
            event.register(toggleMagnetKey);
            event.register(toggleLightWandKey);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            Services.KEY_HANDLER.veinPreviewConsume(veinPreviewKey, player);
            Services.KEY_HANDLER.fastPlaceConsume(fastPlaceKey, player);
            Services.KEY_HANDLER.exactMatchConsume(exactMatchKey, player);
            Services.KEY_HANDLER.openBackpackConsume(openBackpackKey, player);
            Services.KEY_HANDLER.toggleMagnetConsume(toggleMagnetKey, player);
            Services.KEY_HANDLER.toggleLightWandConsume(toggleLightWandKey, player);
            veinKeyDown = veinKey.isDown();
        }
    }
}
