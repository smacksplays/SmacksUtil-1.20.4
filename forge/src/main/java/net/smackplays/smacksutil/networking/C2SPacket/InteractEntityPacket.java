package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.smackplays.smacksutil.items.AdvancedMobCatcherItem;
import net.smackplays.smacksutil.items.MobCatcherItem;

import java.util.List;
import java.util.UUID;

public class InteractEntityPacket {
    private final ItemStack stack;
    private final UUID entityUUID;
    private final boolean isMainHnad;

    public InteractEntityPacket(ItemStack s, UUID u, boolean m) {
        stack = s;
        entityUUID = u;
        isMainHnad = m;
    }

    public InteractEntityPacket(FriendlyByteBuf buffer) {
        stack = buffer.readItem();
        entityUUID = buffer.readUUID();
        isMainHnad = buffer.readBoolean();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeItem(stack);
        buffer.writeUUID(entityUUID);
        buffer.writeBoolean(isMainHnad);
    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if (player == null)
            return;
        Level world = player.level();
        AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
        List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
        LivingEntity livingEntity = null;
        for  (LivingEntity e : entityList){
            if (e.getUUID().equals(entityUUID)){
                livingEntity = e;
            }
        }
        if (livingEntity == null) return;
        InteractionHand hand = isMainHnad ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (stack.getItem() instanceof MobCatcherItem mobItem){
            if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                livingEntity.remove(Entity.RemovalReason.KILLED);
            }
        }
        if (stack.getItem() instanceof AdvancedMobCatcherItem mobItem){
            if (mobItem.pickupLivingEntity(stack, player, livingEntity, hand)){
                livingEntity.remove(Entity.RemovalReason.KILLED);
            }
        }
    }
}
