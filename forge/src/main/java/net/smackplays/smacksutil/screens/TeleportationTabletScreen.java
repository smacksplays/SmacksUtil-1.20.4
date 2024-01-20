package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.networking.TeleportationNBTPacket;
import net.smackplays.smacksutil.networking.TeleportationPacket;

public class TeleportationTabletScreen extends AbstractTeleportationTabletScreen<TeleportationTabletMenu> {
    public TeleportationTabletScreen(TeleportationTabletMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    void sendNBTTeleportPacket(ResourceKey<?> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim){
        PacketHandler.sendToServer(new TeleportationNBTPacket(levelKey, stack, pos, xRot, yRot, name, dim, false));
    }

    @Override
    void sendTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        if(remove){
            PacketHandler.sendToServer(new TeleportationNBTPacket(levelKey, stack, pos, xRot, yRot, name, dim, true));
        } else {
            PacketHandler.sendToServer(new TeleportationPacket(levelKey, pos, xRot, yRot));
        }
    }
}