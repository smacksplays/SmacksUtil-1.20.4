package net.smackplays.smacksutil.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.smackplays.smacksutil.Constants;

@SuppressWarnings("unused")
public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
                    new ResourceLocation(Constants.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SSortPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SSortPacket::encode)
                .decoder(SSortPacket::new)
                .consumerMainThread(SSortPacket::handle)
                .add();

        INSTANCE.messageBuilder(SEnchantPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SEnchantPacket::encode)
                .decoder(SEnchantPacket::new)
                .consumerMainThread(SEnchantPacket::handle)
                .add();

        INSTANCE.messageBuilder(SBreakBlockPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SBreakBlockPacket::encode)
                .decoder(SBreakBlockPacket::new)
                .consumerMainThread(SBreakBlockPacket::handle)
                .add();

        INSTANCE.messageBuilder(TeleportationNBTPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(TeleportationNBTPacket::encode)
                .decoder(TeleportationNBTPacket::new)
                .consumerMainThread(TeleportationNBTPacket::handle)
                .add();

        INSTANCE.messageBuilder(TeleportationPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(TeleportationPacket::encode)
                .decoder(TeleportationPacket::new)
                .consumerMainThread(TeleportationPacket::handle)
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