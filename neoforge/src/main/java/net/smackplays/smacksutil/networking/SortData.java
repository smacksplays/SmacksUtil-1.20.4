package net.smackplays.smacksutil.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record SortData(ItemStack item) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "sort_data");

    public SortData(final FriendlyByteBuf buffer) {
        this(buffer.readItem());
    }

    @Override
    public void write(final FriendlyByteBuf buffer) {
        buffer.writeItem(item);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}