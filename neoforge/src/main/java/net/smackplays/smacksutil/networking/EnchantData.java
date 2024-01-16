package net.smackplays.smacksutil.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;

public record EnchantData(ItemStack item) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "enchant_data");

    public EnchantData(final FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeItem(item);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}