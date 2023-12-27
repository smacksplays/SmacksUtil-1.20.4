package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item {

    public BackpackItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).maxDamageIfAbsent(200));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));
        if (hand.equals(Hand.OFF_HAND)) return TypedActionResult.pass(player.getStackInHand(hand));

        player.openHandledScreen(createScreenHandlerFactory(player.getMainHandStack()));

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) ->
                new BackpackScreenHandler(i, playerInventory, new BackpackInventory(stack)), stack.getName());
    }

}