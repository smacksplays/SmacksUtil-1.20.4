package net.smackplays.smacksutil.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;

import java.util.UUID;

import static net.smackplays.smacksutil.SmacksUtil.*;

public class FabricClientPacketSender implements IClientPacketSender {
    @Override
    public void VeinMinerBreakPacket(ItemStack mainHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(mainHandStack);
        packet.writeBlockPos(pos);
        packet.writeBoolean(isCreative);
        packet.writeBoolean(replaceSeeds);
        if (ClientPlayNetworking.canSend(VEINMINER_BREAK_REQUEST_ID)){
            ClientPlayNetworking.send(VEINMINER_BREAK_REQUEST_ID, packet);
        }
    }

    @Override
    public void EnchantPacket(ItemStack stack) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        if (ClientPlayNetworking.canSend(ENCHANT_REQUEST_ID)){
            ClientPlayNetworking.send(ENCHANT_REQUEST_ID, packet);
        }
    }

    @Override
    public void BackpackSortPacket(ItemStack stack) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        if (ClientPlayNetworking.canSend(BACKPACK_SORT_REQUEST_ID)){
            ClientPlayNetworking.send(BACKPACK_SORT_REQUEST_ID, packet);
        }
    }

    @Override
    public void BackpackOpenPacket(int slot) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeInt(slot);
        if (ClientPlayNetworking.canSend(BACKPACK_OPEN_REQUEST_ID)){
            ClientPlayNetworking.send(BACKPACK_OPEN_REQUEST_ID, packet);
        }
    }

    @Override
    public void SetBlockAirPacket(BlockPos pos) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeBlockPos(pos);
        if (ClientPlayNetworking.canSend(SET_BLOCK_AIR_REQUEST_ID)){
            ClientPlayNetworking.send(SET_BLOCK_AIR_REQUEST_ID, packet);
        }
    }

    @Override
    public void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeResourceKey(levelKey);
        packet.writeVec3(pos);
        packet.writeFloat(xRot);
        packet.writeFloat(yRot);
        if (ClientPlayNetworking.canSend(TELEPORT_REQUEST_ID)){
            ClientPlayNetworking.send(TELEPORT_REQUEST_ID, packet);
        }
    }

    @Override
    public void TeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        packet.writeVec3(pos);
        packet.writeFloat(xRot);
        packet.writeFloat(yRot);
        packet.writeUtf(name);
        packet.writeUtf(dim);
        packet.writeBoolean(remove);
        if (ClientPlayNetworking.canSend(TELEPORT_NBT_REQUEST_ID)){
            ClientPlayNetworking.send(TELEPORT_NBT_REQUEST_ID, packet);
        }
    }

    // hand true => main hand, false => off_hand
    @Override
    public void InteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {
        FriendlyByteBuf packet = PacketByteBufs.create();
        packet.writeItem(stack);
        packet.writeUUID(entityUUID);
        packet.writeBoolean(hand);
        if (ClientPlayNetworking.canSend(INTERACT_ENTITY_REQUEST_ID)){
            ClientPlayNetworking.send(INTERACT_ENTITY_REQUEST_ID, packet);
        }
    }
}
