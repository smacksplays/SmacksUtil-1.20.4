package net.smackplays.smacksutil.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.networking.c2spacket.*;
import net.smackplays.smacksutil.networking.s2cpacket.S2CPlayerBlockBreakPacket;

@SuppressWarnings("unused")
public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
                    ResourceLocation.tryBuild(Constants.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(C2SBackpackSortPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SBackpackSortPacket::encode)
                .decoder(C2SBackpackSortPacket::new)
                .consumerMainThread(C2SBackpackSortPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SBackpackOpenPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SBackpackOpenPacket::encode)
                .decoder(C2SBackpackOpenPacket::new)
                .consumerMainThread(C2SBackpackOpenPacket::handle)
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

        INSTANCE.messageBuilder(C2SVeinMinerBreakPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SVeinMinerBreakPacket::encode)
                .decoder(C2SVeinMinerBreakPacket::new)
                .consumerMainThread(C2SVeinMinerBreakPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SToggleMagnetItemPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SToggleMagnetItemPacket::encode)
                .decoder(C2SToggleMagnetItemPacket::new)
                .consumerMainThread(C2SToggleMagnetItemPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SToggleLightWandItemPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SToggleLightWandItemPacket::encode)
                .decoder(C2SToggleLightWandItemPacket::new)
                .consumerMainThread(C2SToggleLightWandItemPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2CPlayerBlockBreakPacket.class, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(S2CPlayerBlockBreakPacket::encode)
                .decoder(S2CPlayerBlockBreakPacket::new)
                .consumerMainThread(S2CPlayerBlockBreakPacket::handle)
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