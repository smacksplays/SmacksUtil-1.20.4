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
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagnetItem extends Item {

    private static final int GREEN = 65280;
    private static final int RED = 16711680;

    public MagnetItem(Properties props) {
        super(props);
    }

    public MagnetItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (world.isClientSide) return InteractionResultHolder.success(stack);
        if (!player.isCrouching()) {
            toggle(stack, player);
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int $$3, boolean $$4) {
        if (!world.isClientSide && stack.getOrCreateTag().getBoolean("enabled")){
            attract(entity, world, getRange());
        }
        super.inventoryTick(stack, world, entity, $$3, $$4);
    }

    public void attract(Entity entity, Level world, int range){
        AABB area = new AABB(entity.position().add(-range, -range, -range), entity.position().add(range, range, range));
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
    }

    public void toggle(ItemStack stack, Player player){
        boolean state = stack.getOrCreateTag().getBoolean("enabled");
        stack.getOrCreateTag().putBoolean("enabled", !state);
        String msg = !state ? "Active" : "Inactive";
        int color = !state ? GREEN : RED;
        notifyPlayer(player, msg, color);
    }

    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Magnet: " + msg).withColor(color), true);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("enabled");
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> componentList, @NotNull TooltipFlag flag) {
        CompoundTag tag = stack.getOrCreateTag();
        componentList.add(1, Component.literal("Enabled: " + tag.getBoolean("enabled")));
    }

    public int getRange(){
        return 5;
    }
}
