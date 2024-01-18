package net.smackplays.smacksutil.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BackpackUpgradeTier2Item extends BaseBackpackUpgradeItem {
    public BackpackUpgradeTier2Item(Properties props) {
        super(props);
    }
    public BackpackUpgradeTier2Item(){
        super(new Properties().stacksTo(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("Upgrade", 4);
        stack.setTag(tag);
        return stack;
    }
}
