package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.*;
import net.smackplays.smacksutil.networking.C2SPacket.*;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;

import java.util.UUID;

public class ForgeClientPacketSender implements IClientPacketSender {
    @Override
    public void sendToServerVeinMinerBreakPacket(ItemStack mainHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        PacketHandler.sendToServer(new C2SVeinMinerBreakPacket(mainHandStack, pos, isCreative, replaceSeeds));
    }

    @Override
    public void sendToServerEnchantPacket(ItemStack stack) {
        PacketHandler.sendToServer(new C2SEnchantPacket(stack));
    }

    @Override
    public void sendToServerSortPacket(ItemStack stack) {
        PacketHandler.sendToServer(new C2SSortPacket(stack));
    }

    @Override
    public void sendToServerSetBlockAirPacket(BlockPos pos) {
        PacketHandler.sendToServer(new C2SSetBlockAirPacket(pos));
    }

    @Override
    public void sendToServerTeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        PacketHandler.sendToServer(new C2STeleportationPacket(levelKey, pos, xRot, yRot));
    }

    @Override
    public void sendToServerTeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        PacketHandler.sendToServer(new C2STeleportationNBTPacket(stack, pos, xRot, yRot, name, dim, remove));
    }

    @Override
    public void sendToServerInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {
        PacketHandler.sendToServer(new C2SInteractEntityPacket(stack, entityUUID, hand));
    }
}
