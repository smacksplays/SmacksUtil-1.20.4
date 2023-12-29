package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item implements DyeableItem {

    public BackpackItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));
        if (player.isSneaking()) return TypedActionResult.pass(player.getStackInHand(hand));
        if (hand.equals(Hand.OFF_HAND)) return TypedActionResult.pass(player.getStackInHand(hand));

        player.openHandledScreen(createScreenHandlerFactory(player.getMainHandStack()));

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() == null) return ActionResult.FAIL;
        if (context.getPlayer().isSneaking()) return ActionResult.PASS;
        use(context.getWorld(), context.getPlayer(), context.getHand());
        return ActionResult.CONSUME;
    }

    public NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                GenericContainerScreenHandler.createGeneric9x6(i, playerInventory, new BackpackInventory(stack)), stack.getName());
    }
}