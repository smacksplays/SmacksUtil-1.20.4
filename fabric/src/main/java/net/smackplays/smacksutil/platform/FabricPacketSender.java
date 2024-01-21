package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.services.IPacketSender;

import java.util.UUID;

public class FabricPacketSender implements IPacketSender {
    @Override
    public void sendToServerVeinMinerBreakPacket(ItemStack meinHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(meinHandStack);
        packet.writeBlockPos(pos);
        packet.writeBoolean(isCreative);
        packet.writeBoolean(replaceSeeds);
        if (ClientPlayNetworking.canSend(SmacksUtil.VEINMINER_BREAK_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.VEINMINER_BREAK_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerEnchantPacket(ItemStack stack) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        if (ClientPlayNetworking.canSend(SmacksUtil.ENCHANT_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.ENCHANT_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerSortPacket(ItemStack stack) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        if (ClientPlayNetworking.canSend(SmacksUtil.SORT_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.SORT_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerBreakBlockPacket(BlockPos pos) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeBlockPos(pos);
        if (ClientPlayNetworking.canSend(SmacksUtil.BREAK_BLOCK_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.BREAK_BLOCK_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerSetBlockAirPacket(BlockPos pos) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeBlockPos(pos);
        if (ClientPlayNetworking.canSend(SmacksUtil.SET_BLOCK_AIR_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.SET_BLOCK_AIR_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeResourceKey(levelKey);
        packet.writeVec3(pos);
        packet.writeFloat(xRot);
        packet.writeFloat(yRot);
        if (ClientPlayNetworking.canSend(SmacksUtil.TELEPORT_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.TELEPORT_REQUEST_ID, packet);
        }
    }

    @Override
    public void sendToServerTeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        packet.writeVec3(pos);
        packet.writeFloat(xRot);
        packet.writeFloat(yRot);
        packet.writeUtf(name);
        packet.writeUtf(dim);
        packet.writeBoolean(remove);
        if (ClientPlayNetworking.canSend(SmacksUtil.TELEPORT_NBT_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.TELEPORT_NBT_REQUEST_ID, packet);
        }
    }

    // hand true => main hand, false => off_hand
    @Override
    public void sendToServerInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        packet.writeUUID(entityUUID);
        packet.writeBoolean(hand);
        if (ClientPlayNetworking.canSend(SmacksUtil.INTERACT_ENTITY_REQUEST_ID)){
            ClientPlayNetworking.send(SmacksUtil.INTERACT_ENTITY_REQUEST_ID, packet);
        }
    }
}
