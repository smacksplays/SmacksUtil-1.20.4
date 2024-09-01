package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.smackplays.smacksutil.util.PlayerComparator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AutoLightWandItem extends LightWandItem {
    private static final int GREEN = 65280;
    private static final int RED = 16711680;

    public AutoLightWandItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).durability(2000));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (world.isClientSide) return InteractionResultHolder.success(stack);
        if (player.isCrouching()) {
            toggle(stack, player);
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int $$3, boolean $$4) {
        /*if (!world.isClientSide && stack.getOrCreateTag().getBoolean("enabled")) {
            Player player = (Player) entity;

            BlockPos sourcePos = player.blockPosition();
            ArrayList<BlockPos> toDark = new ArrayList<>();

            for (int x = -4; x < 4; x++) {
                for (int y = -2; y < 2; y++) {
                    for (int z = -4; z < 4; z++) {
                        BlockPos curr = sourcePos.offset(x, y, z);
                        int light = world.getBrightness(LightLayer.BLOCK, curr);
                        if (light <= 9 && !world.getBlockState(curr.below()).is(Blocks.AIR)
                                && (world.getBlockState(curr).is(Blocks.AIR)
                                    || world.getBlockState(curr).is(Blocks.CAVE_AIR))
                                && world.getBlockState(curr).getFluidState().isEmpty()
                                && Block.isShapeFullBlock(world.getBlockState(curr.below()).getShape(world, curr))) {
                            toDark.add(curr);
                        }
                    }
                }
            }

            toDark.sort(new PlayerComparator(player));
            if (!toDark.isEmpty()) {
                world.setBlockAndUpdate(toDark.get(0), Blocks.LIGHT.defaultBlockState());
            }
        }*/
        super.inventoryTick(stack, world, entity, $$3, $$4);
    }

    @Override

    public boolean isFoil(@NotNull ItemStack stack) {
        //return stack.getOrCreateTag().getBoolean("enabled");
        return false;
    }

    public void toggle(ItemStack stack, Player player) {
        /*boolean state = stack.getOrCreateTag().getBoolean("enabled");
        stack.getOrCreateTag().putBoolean("enabled", !state);
        String msg = !state ? "Active" : "Inactive";
        int color = !state ? GREEN : RED;
        notifyPlayer(player, msg, color);*/
    }

    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Auto Light Wand: " + msg).withColor(color), true);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> componentList, @NotNull TooltipFlag flag) {
        /*CompoundTag tag = stack.getOrCreateTag();
        componentList.add(1, Component.literal("Enabled: " + tag.getBoolean("enabled")));*/
    }
}
