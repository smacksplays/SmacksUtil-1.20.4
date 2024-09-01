package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BackpackUpgradeItem extends Item {
    private int multiplier = 1;
    @SuppressWarnings("unused")
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
    }
    public BackpackUpgradeItem(int multiplier){
        super(new Item.Properties().stacksTo(1));
        this.multiplier = multiplier;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, List<Component> componentList, @NotNull TooltipFlag flag) {
        componentList.add(1, Component.literal("Multiplier: " + multiplier));
        super.appendHoverText(stack, context, componentList, flag);
    }

    public int getMultiplier(){
        return multiplier;
    }
}
