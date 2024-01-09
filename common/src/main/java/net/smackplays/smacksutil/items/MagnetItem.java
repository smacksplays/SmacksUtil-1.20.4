package net.smackplays.smacksutil.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class MagnetItem extends Item {

    private static final int GREEN = 65280;
    private static final int RED = 16711680;
    public boolean enable_magnet;

    public MagnetItem(Properties $$0) {
        super($$0);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand interactionHand) {
        if (world.isClientSide) return super.use(world, player, interactionHand);
        if (player.isCrouching()) {

        } else {
            ItemStack stack = player.getItemInHand(interactionHand);
            CompoundTag tag = stack.getTag();
            if (tag == null) {
                stack.getOrCreateTag();
                tag = stack.getTag();
            }
            setEnable_magnet(tag.getBoolean("enabled"));
            stack.getOrCreateTag().putBoolean("enabled", !isEnable_magnet(stack));
            boolean auto_wand = isEnable_magnet(stack);
            String str = auto_wand ? "Active" : "Inactive";
            int color = auto_wand ? GREEN : RED;
            player.displayClientMessage(Component
                    .literal("Magnet: " + str).withColor(color), true);
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int $$3, boolean $$4) {
        if (world.isClientSide) return;
        if (!isEnable_magnet(stack)) return;
        AABB area = new AABB(entity.position().add(-5, -5, -5), entity.position().add(5, 5, 5));
        Player player = (Player) entity;
        List<ItemEntity> entities = world.getEntitiesOfClass(ItemEntity.class, area);
        for (ItemEntity e : entities) {
            e.setPos(player.position());
            e.setPickUpDelay(0);
        }

        List<ExperienceOrb> orbs = world.getEntitiesOfClass(ExperienceOrb.class, area);
        for (ExperienceOrb e : orbs) {
            player.takeXpDelay = 0;
            e.playerTouch(player);
        }

        super.inventoryTick(stack, world, entity, $$3, $$4);
    }

    public boolean isEnable_magnet(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        enable_magnet = tag.getBoolean("enabled");
        return enable_magnet;
    }

    public void setEnable_magnet(boolean enable_magnet) {
        this.enable_magnet = enable_magnet;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isEnable_magnet(stack);
    }
}
