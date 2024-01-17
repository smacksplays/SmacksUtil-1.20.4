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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.smackplays.smacksutil.util.PlayerComparator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AutoLightWandItem extends LightWandItem {
    private static final int GREEN = 65280;
    private static final int RED = 16711680;
    public boolean enable_wand;

    public AutoLightWandItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).durability(2000));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (world.isClientSide) return InteractionResultHolder.success(stack);
        if (player.isCrouching()) {
            CompoundTag tag = stack.getOrCreateTag();
            setEnable_wand(tag.getBoolean("enabled"));
            stack.getOrCreateTag().putBoolean("enabled", !isEnable_wand(stack));
            boolean auto_wand = isEnable_wand(stack);
            String str = auto_wand ? "Active" : "Inactive";
            int color = auto_wand ? GREEN : RED;
            player.displayClientMessage(Component
                    .literal("Auto Wand: " + str).withColor(color), true);
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int $$3, boolean $$4) {
        if (!world.isClientSide && isEnable_wand(stack)) {
            Player player = (Player) entity;

            BlockPos sourcePos = player.blockPosition();
            BlockPos pos = new BlockPos(sourcePos.getX() - 4, sourcePos.getY() - 2, sourcePos.getZ() - 4);
            ArrayList<BlockPos> toDark = new ArrayList<>();

            for (int x = 0; x < 4 * 2; x++) {
                for (int y = 0; y < 5; y++) {
                    for (int z = 0; z < 4 * 2; z++) {
                        BlockPos curr = pos.offset(x, y, z);
                        int light = world.getBrightness(LightLayer.BLOCK, curr);
                        if (light <= 9 && !world.getBlockState(curr.below()).is(Blocks.AIR)
                                && world.getBlockState(curr).is(Blocks.AIR)
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
        }
        super.inventoryTick(stack, world, entity, $$3, $$4);
    }

    public boolean isEnable_wand(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        enable_wand = tag.getBoolean("enabled");
        return enable_wand;
    }

    public void setEnable_wand(boolean enable_wand) {
        this.enable_wand = enable_wand;
    }

    @Override

    public boolean isFoil(@NotNull ItemStack stack) {
        return isEnable_wand(stack);
    }
}
