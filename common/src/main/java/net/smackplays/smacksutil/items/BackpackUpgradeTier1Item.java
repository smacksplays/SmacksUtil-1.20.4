package net.smackplays.smacksutil.items;

import net.minecraft.world.item.Item;

public class BackpackUpgradeTier1Item extends BaseBackpackUpgradeItem {
    public BackpackUpgradeTier1Item(Properties props) {
        super(props);
    }
    public BackpackUpgradeTier1Item(){
        super(new Item.Properties().stacksTo(1));
    }
}
