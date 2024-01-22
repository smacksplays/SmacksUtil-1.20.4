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
    public void sendToServerVeinMinerBreakPacket(ItemStack meinHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {

    }

    @Override
    public void sendToServerEnchantPacket(ItemStack stack) {
        PacketHandler.sendToServer(new SEnchantPacket(stack));
    }

    @Override
    public void sendToServerSortPacket(ItemStack stack) {
        PacketHandler.sendToServer(new SSortPacket(stack));
    }

    @Override
    public void sendToServerSetBlockAirPacket(BlockPos pos) {
        PacketHandler.sendToServer(new SetBlockAirPacket(pos));
    }

    @Override
    public void sendToServerTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot) {
        PacketHandler.sendToServer(new TeleportationPacket(levelKey, pos, xRot, yRot));
    }

    @Override
    public void sendToServerTeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        PacketHandler.sendToServer(new TeleportationNBTPacket(stack, pos, xRot, yRot, name, dim, remove));
    }

    @Override
    public void sendToServerInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {
        PacketHandler.sendToServer(new InteractEntityPacket(stack, entityUUID, hand));
    }
}
