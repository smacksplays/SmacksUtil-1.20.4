package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class C2SEnchantPacket {
    private final ItemStack stack;

    public C2SEnchantPacket(ItemStack s) {
        stack = s;
    }

    public C2SEnchantPacket(FriendlyByteBuf buffer) {
        stack = buffer.readItem();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        AbstractContainerMenu screenHandler = player.containerMenu;
        screenHandler.slots.get(0).set(stack);
    }
}