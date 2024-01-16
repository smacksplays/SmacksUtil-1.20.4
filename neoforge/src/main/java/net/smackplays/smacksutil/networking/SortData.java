package net.smackplays.smacksutil.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.smackplays.smacksutil.Constants;
import org.jetbrains.annotations.NotNull;

public record SortData() implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "sort_data");

    @SuppressWarnings("unused")
    public SortData(final FriendlyByteBuf buffer) {
        this();
    }

    @Override
    public void write(final @NotNull FriendlyByteBuf buffer) {

    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}