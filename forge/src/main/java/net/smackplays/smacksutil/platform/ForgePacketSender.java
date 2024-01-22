package net.smackplays.smacksutil.platform;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.platform.services.IPacketSender;

import java.util.UUID;

public class ForgePacketSender implements IPacketSender {
    @Override
    public void sendToServerVeinMinerBreakPacket(ItemStack meinHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {

    }

    @Override
    public void sendToServerEnchantPacket(ItemStack stack) {

    }

    @Override
    public void sendToServerSortPacket(ItemStack stack) {

    }

    @Override
    public void sendToServerBreakBlockPacket(BlockPos pos) {

    }

    @Override
    public void sendToServerSetBlockAirPacket(BlockPos pos) {

    }

    @Override
    public void sendToServerTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot) {

    }

    @Override
    public void sendToServerTeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {

    }

    @Override
    public void sendToServerInteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {

    }

    @Override
    public void sendToPlayerBlockBreakPacket(ServerPlayer player, BlockPos pos) {

    }
}
