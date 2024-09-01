package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class MobCatcherItem extends Item {

    private boolean isHolding;

    public MobCatcherItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        /*Level world = context.getLevel();
        ItemStack stack = context.getItemInHand();
        CompoundTag tag = stack.getOrCreateTag();
        BlockPos clicked = context.getClickedPos();
        if (!world.isClientSide && isHolding(stack) && world.getBlockState(clicked.above()).is(Blocks.AIR)) {
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
            stack.setTag(new CompoundTag());
        }*/
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("unused")
    public boolean pickupLivingEntity(@NotNull ItemStack stack, Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        /*Level world = player.level();
        ItemStack mainHandStack = player.getItemInHand(interactionHand);
        if (!isHolding(mainHandStack) && !world.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag tag = new CompoundTag();
            livingEntity.save(tag);
            livingEntity.addAdditionalSaveData(tag);
            mainHandStack.setTag(tag);
            mainHandStack.getOrCreateTag().putBoolean("is_Holding", true);
            setHolding(true);
            return true;
        }*/
        return false;
    }

    public boolean isHolding(ItemStack stack) {
        /*CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("is_Holding")) {
            isHolding = tag.getBoolean("is_Holding");
        } else {
            return false;
        }
        return isHolding;*/
        return false;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext context, @NotNull List<Component> componentList, @NotNull TooltipFlag flag) {
        /*CompoundTag tag = itemStack.getOrCreateTag();
        if (!tag.isEmpty()) {
            Component storedEntity = Component.literal("Entity: " + tag.getString("id"));
            componentList.add(storedEntity);
        }
*/
        super.appendHoverText(itemStack, context, componentList, flag);
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
