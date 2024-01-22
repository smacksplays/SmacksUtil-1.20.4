package net.smackplays.smacksutil.networking;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.nio.charset.StandardCharsets;

public class TeleportationNBTPacket {
    private final ItemStack stack;
    private final Vec3 pos;
    private final float xRot;
    private final float yRot;
    private final String name;
    private final String dim;
    private final boolean remove;

    public TeleportationNBTPacket(ItemStack stack, Vec3 pos, float xRot, float yRot, String name, String dim, boolean remove) {
        this.stack = stack;
        this.pos = pos;
        this.xRot = xRot;
        this.yRot = yRot;
        this.name = name;
        this.dim = dim;
        this.remove = remove;
    }

    public TeleportationNBTPacket(FriendlyByteBuf buffer) {
        stack = buffer.readItem();
        pos = buffer.readVec3();
        xRot = buffer.readFloat();
        yRot = buffer.readFloat();
        name = buffer.readUtf();
        dim = buffer.readUtf();
        remove = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
        buffer.writeVec3(pos);
        buffer.writeFloat(xRot);
        buffer.writeFloat(yRot);
        buffer.writeByteArray(name.getBytes(StandardCharsets.UTF_8));
        buffer.writeByteArray(dim.getBytes(StandardCharsets.UTF_8));
        buffer.writeBoolean(remove);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;

        CompoundTag tag = stack.getOrCreateTag();
        ListTag posTag = (ListTag)tag.get("Positions");
        if (posTag == null) {
            posTag = new ListTag();
        }
        CompoundTag teleportTag = new CompoundTag();
        teleportTag.putString("name", name);
        teleportTag.putDouble("x_pos", pos.x());
        teleportTag.putDouble("y_pos", pos.y());
        teleportTag.putDouble("z_pos", pos.z());
        teleportTag.putDouble("x_rot", xRot);
        teleportTag.putDouble("y_rot", yRot);
        teleportTag.putString("dim", dim);
        if (remove){
            for (int i = 0; i < posTag.size(); i++){
                CompoundTag t = (CompoundTag) posTag.get(i);
                if (t.contains("name")){
                    String n = t.getString("name");
                    if (n.equals(name)){
                        posTag.remove(i);
                        tag.put("Positions", posTag);
                        stack.setTag(tag);
                        player.getInventory().setItem(player.getInventory().selected, stack);
                        player.inventoryMenu.broadcastChanges();
                        return;
                    }
                }
            }
        } else {
            posTag.add(teleportTag);
        }
        tag.put("Positions", posTag);
        stack.setTag(tag);
        player.getInventory().setItem(player.getInventory().selected, stack);
        player.inventoryMenu.broadcastChanges();
    }
}