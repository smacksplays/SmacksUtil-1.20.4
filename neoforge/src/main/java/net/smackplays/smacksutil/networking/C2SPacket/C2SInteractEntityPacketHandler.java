package net.smackplays.smacksutil.networking.C2SPacket;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import net.smackplays.smacksutil.items.AdvancedMobCatcherItem;
import net.smackplays.smacksutil.items.MobCatcherItem;

import java.util.List;

public class C2SInteractEntityPacketHandler {
    private static final C2SInteractEntityPacketHandler INSTANCE = new C2SInteractEntityPacketHandler();

    public static C2SInteractEntityPacketHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final C2SInteractEntityPacket data, final PlayPayloadContext context) {
        // Do something with the data, on the network thread

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
            ItemStack stack = data.stack();
            if (context.player().isPresent()) {
                Player player = context.player().get();
                Level world = player.level();
                AABB aabb = new AABB(player.position().add(-5,-5,-5), player.position().add(5,5,5));
                List<LivingEntity> entityList = world.getEntitiesOfClass(LivingEntity.class, aabb, entity -> true);
                LivingEntity livingEntity = null;
                for  (LivingEntity e : entityList){
                    if (e.getUUID().equals(data.entityUUID())){
                        livingEntity = e;
                    }
                }
                if (livingEntity == null) return;
                InteractionHand hand = data.hand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
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
        });
    }
}
