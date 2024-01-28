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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class AdvancedMobCatcherItem extends Item {
    private boolean isHolding;

    public AdvancedMobCatcherItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag mainTag = stack.getOrCreateTag();
        ListTag listTag = (ListTag) mainTag.get("Entities");
        if (listTag != null && !listTag.isEmpty()) {
            CompoundTag tag = (CompoundTag) listTag.get(0);
            BlockPos clicked = context.getClickedPos();
            if (!world.isClientSide && isHolding(stack)
                    && world.getBlockState(clicked.above()).getCollisionShape(world, clicked.above()).isEmpty()) {
                Entity toCreate = EntityType.loadEntityRecursive(tag, world, entity -> {
                    entity.moveTo(
                            clicked.above(),
                            entity.getYRot(),
                            entity.getXRot());
                    return entity;
                });
                if (toCreate == null) return InteractionResult.SUCCESS;
                toCreate.setUUID(UUID.randomUUID());
                toCreate.setPos(context.getClickedPos().above().getCenter().add(0, -0.5, 0));
                world.addFreshEntity(toCreate);
                listTag.remove(tag);
                if (listTag.isEmpty()) {
                    mainTag.putBoolean("is_Holding", false);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("unused")
    public boolean pickupLivingEntity(@NotNull ItemStack stack, Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
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
            livingEntity.addAdditionalSaveData(entityTag);
            if (!list.contains(entityTag)) {
                list.add(entityTag);
            }
            tag.put("Entities", list);
            //mainHandStack.setTag(entityTag);
            mainHandStack.getOrCreateTag().putBoolean("is_Holding", true);
            setHolding(true);
            return true;
        }
        return false;
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
            return listTag != null && listTag.size() < 10;
        } else {
            return true;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, @NotNull List<Component> componentList, @NotNull TooltipFlag flag) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.isEmpty()) {
            ListTag listTag = (ListTag) tag.get("Entities");
            if (listTag == null) return;
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
    public boolean isFoil(@NotNull ItemStack stack) {
        return isHolding(stack);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }
}
