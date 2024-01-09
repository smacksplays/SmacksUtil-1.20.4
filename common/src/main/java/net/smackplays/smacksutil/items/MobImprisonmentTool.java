package net.smackplays.smacksutil.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class MobImprisonmentTool extends Item {

    private boolean isHolding;

    public MobImprisonmentTool(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();
        if (!world.isClientSide && isHolding(stack)) {
            Entity toCreate = EntityType.loadEntityRecursive(tag, world, entity -> {
                entity.moveTo(
                        context.getClickedPos().getX(),
                        context.getClickedPos().getY() + 1,
                        context.getClickedPos().getZ(),
                        entity.getYRot(),
                        entity.getXRot());
                return entity;
            });
            toCreate.setUUID(UUID.randomUUID());
            toCreate.setPos(context.getClickedPos().above().getCenter());
            world.addFreshEntity(toCreate);
            stack.setTag(new CompoundTag());
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        Level world = player.level();
        ItemStack mainHandStack = player.getItemInHand(interactionHand);
        if (!isHolding(mainHandStack) && !world.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag tag = new CompoundTag();
            livingEntity.save(tag);
            livingEntity.addAdditionalSaveData(tag);
            mainHandStack.setTag(tag);
            mainHandStack.getOrCreateTag().putBoolean("is_Holding", true);
            setHolding(true);
            livingEntity.remove(Entity.RemovalReason.KILLED);
        }
        return super.interactLivingEntity(stack, player, livingEntity, interactionHand);
    }

    public boolean isHolding(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("is_Holding")) {
            isHolding = tag.getBoolean("is_Holding");
        } else {
            return false;
        }
        return isHolding;
    }

    public void setHolding(boolean holding) {
        isHolding = holding;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isHolding(stack);
    }
}
