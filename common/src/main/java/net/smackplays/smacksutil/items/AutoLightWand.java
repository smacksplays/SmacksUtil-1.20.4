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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.smackplays.smacksutil.util.BlockPosComparator;
import net.smackplays.smacksutil.util.PlayerComparator;

import java.util.ArrayList;

public class AutoLightWand extends LightWand {
    private static final int GREEN = 65280;
    private static final int RED = 16711680;
    public boolean enable_wand;

    public AutoLightWand(Item.Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand interactionHand) {
        if (world.isClientSide) return super.use(world, player, interactionHand);
        if (player.isCrouching()) {
            ItemStack stack = player.getItemInHand(interactionHand);
            CompoundTag tag = stack.getOrCreateTag();
            setEnable_wand(tag.getBoolean("enabled"));
            stack.getOrCreateTag().putBoolean("enabled", !isEnable_wand(stack));
            boolean auto_wand = isEnable_wand(stack);
            String str = auto_wand ? "Active" : "Inactive";
            int color = auto_wand ? GREEN : RED;
            player.displayClientMessage(Component
                    .literal("Auto Wand: " + str).withColor(color), true);
        }
        return super.use(world, player, interactionHand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int $$3, boolean $$4) {
        if (!world.isClientSide && isEnable_wand(stack)){
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

    public boolean isFoil(ItemStack stack) {
        return isEnable_wand(stack);
    }
}
