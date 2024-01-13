package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class AdvancedMobImpTool extends Item {
    private boolean isHolding;

    public AdvancedMobImpTool(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag mainTag = stack.getOrCreateTag();
        ListTag listTag = (ListTag) mainTag.get("Entities");
        if (listTag != null && !listTag.isEmpty()) {
            CompoundTag tag = (CompoundTag) listTag.get(0);
            BlockPos clicked = context.getClickedPos();
            if (!world.isClientSide && isHolding(stack) && world.getBlockState(clicked.above()).is(Blocks.AIR)) {
                Entity toCreate = EntityType.loadEntityRecursive(tag, world, entity -> {
                    entity.moveTo(
                            clicked.above(),
                            entity.getYRot(),
                            entity.getXRot());
                    return entity;
                });
                if (toCreate == null) return super.useOn(context);
                toCreate.setUUID(UUID.randomUUID());
                toCreate.setPos(context.getClickedPos().above().getCenter().add(0, -0.5, 0));
                world.addFreshEntity(toCreate);
                listTag.remove(tag);
                if (listTag.isEmpty()) {
                    mainTag.putBoolean("is_Holding", false);
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        Level world = player.level();
        ItemStack mainHandStack = player.getItemInHand(interactionHand);
        CompoundTag tag = mainHandStack.getOrCreateTag();
        ListTag list;
        if (!tag.contains("Entities")) {
            list = new ListTag();
            tag.put("Entities", list);
        }
        list = (ListTag) tag.get("Entities");
        if (list != null && isBelowMax(mainHandStack) && !world.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag entityTag = new CompoundTag();
            livingEntity.save(entityTag);
            if (entityTag.isEmpty()) {
                return InteractionResult.CONSUME;
            }
            livingEntity.addAdditionalSaveData(entityTag);
            if (!list.contains(entityTag)) {
                list.add(entityTag);
            }
            tag.put("Entities", list);
            //mainHandStack.setTag(entityTag);
            mainHandStack.getOrCreateTag().putBoolean("is_Holding", true);
            setHolding(true);
            livingEntity.remove(Entity.RemovalReason.KILLED);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
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

    public boolean isBelowMax(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("is_Holding")) {
            ListTag listTag = (ListTag) tag.get("Entities");
            if (listTag.size() < 10) return true;
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> componentList, TooltipFlag flag) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.isEmpty()) {
            ListTag listTag = (ListTag) tag.get("Entities");
            for (Tag ltag : listTag) {
                CompoundTag compoundTag = (CompoundTag) ltag;
                Component storedEntity = Component.literal("Entity: " + compoundTag.getString("id"));
                componentList.add(storedEntity);
            }
        }

        super.appendHoverText(itemStack, world, componentList, flag);
    }

    public void setHolding(boolean holding) {
        isHolding = holding;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isHolding(stack);
    }
}
