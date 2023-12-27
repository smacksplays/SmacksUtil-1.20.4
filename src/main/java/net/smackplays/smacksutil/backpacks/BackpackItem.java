package net.smackplays.smacksutil.backpacks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BackpackItem extends Item implements ImplementedInventory{

    public final int BACKPACK_SIZE = 54;
    ImplementedInventory backpack_inv;
    DefaultedList<ItemStack> items;

    public BackpackItem() {
        super(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).maxDamageIfAbsent(200));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(backpack_inv == null || items == null){
            backpack_inv = ImplementedInventory.ofSize(BACKPACK_SIZE);
            items = backpack_inv.getItems();
        }
        ItemStack stack = player.getMainHandStack();
        BackpackItem item = (BackpackItem)stack.getItem();
        if(!world.isClient){
            player.openHandledScreen(new NamedScreenHandlerFactory() {
                @Nullable
                @Override
                public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new BackpackScreenHandler(syncId, item.backpack_inv, player);
                }

                @Override
                public Text getDisplayName() {
                    return Text.translatable("screen.backpack.item");
                }
            });
        }
        return super.use(world, player, hand);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}