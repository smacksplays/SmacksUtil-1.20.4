package net.smackplays.smacksutil.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class LightWand extends Item {


    public LightWand() {
        super(new Item.Properties().rarity(Rarity.EPIC).durability(200));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Direction face = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (world.isClientSide) return super.useOn(context);
        BlockPos toPlace = pos.relative(face, 1);

        if(world.getBlockState(toPlace).is(Blocks.AIR) && !player.isCrouching()){
            world.setBlockAndUpdate(toPlace, Blocks.LIGHT.defaultBlockState());
            if (!player.isCreative()){
                context.getItemInHand().hurt(1, player.getRandom(), (ServerPlayer) player);
            }
        }
        return super.useOn(context);
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }
}