package net.smackplays.smacksutil.networking.S2CPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import net.smackplays.smacksutil.platform.Services;

public class PlayerBlockBreakPacket {
    private final BlockPos pos;

    public PlayerBlockBreakPacket(BlockPos p) {
        pos = p;
    }

    public PlayerBlockBreakPacket(FriendlyByteBuf buffer) {
        pos = buffer.readBlockPos();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(pos);
    }

    public void handle(CustomPayloadEvent.Context context) {
        context.enqueueWork(() ->{
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PlayerBlockBreakPacketHandler.handle(pos));
        });
    }
}
