package net.smackplays.smacksutil.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BackpackUpgradeTier3Item extends BaseBackpackUpgradeItem {
    public BackpackUpgradeTier3Item(Properties props) {
        super(props);
    }
    public BackpackUpgradeTier3Item(){
        super(new Properties().stacksTo(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("Upgrade", 8);
        stack.setTag(tag);
        return stack;
    }
}
