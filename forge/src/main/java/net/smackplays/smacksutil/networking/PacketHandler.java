package net.smackplays.smacksutil.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.networking.C2SPacket.*;
import net.smackplays.smacksutil.networking.S2CPacket.PlayerBlockBreakPacket;
import net.smackplays.smacksutil.platform.Services;

@SuppressWarnings("unused")
public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
                    new ResourceLocation(Constants.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(C2SSortPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SSortPacket::encode)
                .decoder(C2SSortPacket::new)
                .consumerMainThread(C2SSortPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SSetBlockAirPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SSetBlockAirPacket::encode)
                .decoder(C2SSetBlockAirPacket::new)
                .consumerMainThread(C2SSetBlockAirPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SBreakBlockPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SBreakBlockPacket::encode)
                .decoder(C2SBreakBlockPacket::new)
                .consumerMainThread(C2SBreakBlockPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SEnchantPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SEnchantPacket::encode)
                .decoder(C2SEnchantPacket::new)
                .consumerMainThread(C2SEnchantPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2STeleportationNBTPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2STeleportationNBTPacket::encode)
                .decoder(C2STeleportationNBTPacket::new)
                .consumerMainThread(C2STeleportationNBTPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2STeleportationPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2STeleportationPacket::encode)
                .decoder(C2STeleportationPacket::new)
                .consumerMainThread(C2STeleportationPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SInteractEntityPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SInteractEntityPacket::encode)
                .decoder(C2SInteractEntityPacket::new)
                .consumerMainThread(C2SInteractEntityPacket::handle)
                .add();

        INSTANCE.messageBuilder(VeinMinerBreakPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(VeinMinerBreakPacket::encode)
                .decoder(VeinMinerBreakPacket::new)
                .consumerMainThread(VeinMinerBreakPacket::handle)
                .add();

        INSTANCE.messageBuilder(PlayerBlockBreakPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(PlayerBlockBreakPacket::encode)
                .decoder(PlayerBlockBreakPacket::new)
                .consumerMainThread(PlayerBlockBreakPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}