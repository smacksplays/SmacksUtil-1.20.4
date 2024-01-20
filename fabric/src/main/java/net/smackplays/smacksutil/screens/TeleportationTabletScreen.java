package net.smackplays.smacksutil.screens;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.ModClient;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;

public class TeleportationTabletScreen extends AbstractTeleportationTabletScreen<TeleportationTabletMenu> {
    public TeleportationTabletScreen(TeleportationTabletMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    void sendNBTTeleportPacket(ResourceKey<?> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim){
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        packet.writeVec3(pos);
        packet.writeFloat(xRot);
        packet.writeFloat(yRot);
        packet.writeUtf(name);
        packet.writeUtf(dim);
        packet.writeBoolean(false);
        if (ClientPlayNetworking.canSend(ModClient.TELEPORT_NBT_REQUEST_ID)){
            ClientPlayNetworking.send(ModClient.TELEPORT_NBT_REQUEST_ID, packet);
        }
    }

    @Override
    void sendTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        if(remove){
            FriendlyByteBuf packet = PacketByteBufs.create();
            packet.writeItem(stack);
            packet.writeVec3(pos);
            packet.writeFloat(xRot);
            packet.writeFloat(yRot);
            packet.writeUtf(name);
            packet.writeUtf(dim);
            packet.writeBoolean(true);
            if (ClientPlayNetworking.canSend(ModClient.TELEPORT_NBT_REQUEST_ID)){
                ClientPlayNetworking.send(ModClient.TELEPORT_NBT_REQUEST_ID, packet);
            }
        } else {
            FriendlyByteBuf packet = PacketByteBufs.create();
            packet.writeResourceKey(levelKey);
            packet.writeVec3(pos);
            packet.writeFloat(xRot);
            packet.writeFloat(yRot);
            if (ClientPlayNetworking.canSend(ModClient.TELEPORT_REQUEST_ID)){
                ClientPlayNetworking.send(ModClient.TELEPORT_REQUEST_ID, packet);
            }
        }
    }
}