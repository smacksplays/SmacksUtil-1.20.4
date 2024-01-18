package net.smackplays.smacksutil.items;

import net.minecraft.world.item.Item;

public class BaseBackpackUpgradeItem extends Item {
    public BaseBackpackUpgradeItem(Properties props) {
        super(props);
    }
    public BaseBackpackUpgradeItem(){
        super(new Properties().stacksTo(1));
    }

}
