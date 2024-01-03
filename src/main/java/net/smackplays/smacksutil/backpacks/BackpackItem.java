package net.smackplays.smacksutil.backpacks;


import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BackpackItem extends Item implements DyeableLeatherItem {

    public BackpackItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
        if (player.isCrouching()) return InteractionResultHolder.pass(stack);
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResultHolder.pass(stack);

        player.openMenu(createScreenHandlerFactory(player.getMainHandItem()));

        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() == null) return InteractionResult.FAIL;
        if (context.getPlayer().isCrouching()) return InteractionResult.PASS;
        use(context.getLevel(), context.getPlayer(), context.getHand());
        return InteractionResult.CONSUME;
    }

    public MenuProvider createScreenHandlerFactory(ItemStack stack) {
        return new SimpleMenuProvider((i, playerInventory, playerEntity) ->
                ChestMenu.sixRows(i, playerInventory, new BackpackInventory(stack)), stack.getHoverName());
    }
}