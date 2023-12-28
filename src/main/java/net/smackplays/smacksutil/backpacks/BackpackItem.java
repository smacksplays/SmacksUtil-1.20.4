package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BackpackItem extends Item implements DyeableItem {

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
                GenericContainerScreenHandler.createGeneric9x6(i, playerInventory, new BackpackInventory(stack)), stack.getName());
    }
}