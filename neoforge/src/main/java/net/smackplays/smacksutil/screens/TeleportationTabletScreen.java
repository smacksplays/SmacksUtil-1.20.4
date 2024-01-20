package net.smackplays.smacksutil.screens;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.TeleportationData;
import net.smackplays.smacksutil.networking.TeleportationNBTData;

import java.util.Objects;

public class TeleportationTabletScreen extends AbstractTeleportationTabletScreen<TeleportationTabletMenu> {
    public TeleportationTabletScreen(TeleportationTabletMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    void sendNBTTeleportPacket(ResourceKey<?> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim){
        TeleportationNBTData data = new TeleportationNBTData(levelKey, stack, pos, xRot, yRot, name, dim, false);
        if (this.minecraft != null) {
            Objects.requireNonNull(this.minecraft.getConnection()).send(data);
        }
    }

    @Override
    void sendTeleportPacket(ResourceKey<Level> levelKey, ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        CustomPacketPayload data;
        if(remove){
            data = new TeleportationNBTData(levelKey, stack, pos, xRot, yRot, name, dim, true);
        } else {
            data = new TeleportationData(levelKey, pos, xRot, yRot);
        }
        if (this.minecraft != null) {
            Objects.requireNonNull(this.minecraft.getConnection()).send(data);
        }
    }
}