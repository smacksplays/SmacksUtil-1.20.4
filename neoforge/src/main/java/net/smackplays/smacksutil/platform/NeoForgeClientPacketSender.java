package net.smackplays.smacksutil.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.networking.C2SPacket.*;
import net.smackplays.smacksutil.platform.services.IClientPacketSender;

import java.util.Objects;
import java.util.UUID;

public class NeoForgeClientPacketSender implements IClientPacketSender {
    @Override
    public void VeinMinerBreakPacket(ItemStack mainHandStack, BlockPos pos, boolean isCreative, boolean replaceSeeds) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SVeinMinerBreakPacket(mainHandStack, pos, isCreative, replaceSeeds));
        //PacketHandler.sendToServer(new VeinMinerBreakPacket(mainHandStack, pos, isCreative, replaceSeeds));
    }

    @Override
    public void EnchantPacket(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SEnchantPacket(stack));
        //PacketHandler.sendToServer(new C2SEnchantPacket(stack));
    }

    @Override
    public void BackpackSortPacket(ItemStack stack) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SBackpackSortPacket(stack));
        //PacketHandler.sendToServer(new C2SSortPacket(stack));
    }

    @Override
    public void BackpackOpenPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SBackpackOpenPacket(slot));
    }

    @Override
    public void ToggleMagnetItemPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SToggleMagnetItemPacket(slot));
    }

    @Override
    public void ToggleLightWandItemPacket(int slot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SToggleLightWandItemPacket(slot));
    }

    @Override
    public void SetBlockAirPacket(BlockPos pos) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SSetBlockAirPacket(pos));
        //PacketHandler.sendToServer(new C2SSetBlockAirPacket(pos));
    }

    @Override
    public void TeleportPacket(ResourceKey<Level> levelKey, Vec3 pos, float xRot, float yRot) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2STeleportationPacket(levelKey, pos, xRot, yRot));
        //PacketHandler.sendToServer(new C2STeleportationPacket(levelKey, pos, xRot, yRot));
    }

    @Override
    public void TeleportNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2STeleportationNBTPacket(stack, pos, xRot, yRot, name, dim, remove));
        //PacketHandler.sendToServer(new C2STeleportationNBTPacket(stack, pos, xRot, yRot, name, dim, remove));
    }

    @Override
    public void InteractEntityPacket(ItemStack stack, UUID entityUUID, boolean hand) {
        Minecraft minecraft = Minecraft.getInstance();
        Objects.requireNonNull(minecraft.getConnection()).send(new C2SInteractEntityPacket(stack, entityUUID, hand));
        //PacketHandler.sendToServer(new C2SInteractEntityPacket(stack, entityUUID, hand));
    }
}
