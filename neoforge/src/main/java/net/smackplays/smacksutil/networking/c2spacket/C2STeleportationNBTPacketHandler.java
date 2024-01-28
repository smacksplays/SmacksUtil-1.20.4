package net.smackplays.smacksutil.networking.c2spacket;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class C2STeleportationNBTPacketHandler {
    private static final C2STeleportationNBTPacketHandler INSTANCE = new C2STeleportationNBTPacketHandler();

    public static C2STeleportationNBTPacketHandler getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unused")
    public void handleData(final C2STeleportationNBTPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread
        ItemStack stack = data.stack();
        Vec3 pos = data.pos();
        String name = data.name();
        float xRot = data.xRot();
        float yRot = data.yRot();
        boolean remove = data.remove();
        String dim = data.dim();
        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            if (context.level().isPresent() && context.player().isPresent()) {
                Player player = context.player().get();
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
        });
    }
}
