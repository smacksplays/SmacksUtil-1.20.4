package net.smackplays.smacksutil.backpacks;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LargeBackpackItem extends Item implements DyeableItem {

    public LargeBackpackItem() {
        super(new Settings().maxCount(1).rarity(Rarity.EPIC).maxDamageIfAbsent(200));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(world.isClient) return TypedActionResult.pass(player.getStackInHand(hand));
        if (hand.equals(Hand.OFF_HAND)) return TypedActionResult.pass(player.getStackInHand(hand));

        player.openHandledScreen(createScreenHandlerFactory(player.getMainHandStack()));

        return TypedActionResult.pass(player.getStackInHand(hand));
    }
    /*

    (i, playerInventory, playerEntity) ->
     LargeBackpackScreenHandler.createGeneric13x9(i, playerInventory, new LargeBackpackInventory(stack)), stack.getName()
     */

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                buf.writeBoolean(player != null);
            }

            @Override
            public Text getDisplayName() {
                return stack.getName();
            }

            @Nullable
            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                return new LargeBackpackScreenHandler(syncId, playerInventory, new LargeBackpackInventory(stack));
            }
        };
    }
}