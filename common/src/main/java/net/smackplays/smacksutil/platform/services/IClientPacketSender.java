package net.smackplays.smacksutil.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public interface IClientPacketSender {
    void sendToServerVeinMinerBreakPacket(ItemStack mainHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds);

    void sendToServerEnchantPacket(ItemStack stack);

    void sendToServerSortPacket(ItemStack stack);

    void sendToServerSetBlockAirPacket(BlockPos pos);

    void sendToServerTeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot);

    void sendToServerTeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove);

    void sendToServerInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand);
}
