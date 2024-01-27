package net.smackplays.smacksutil.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BackpackUpgradeItem extends Item {
    public BackpackUpgradeItem(Properties props, int multiplier) {
        super(props);
    }
    public BackpackUpgradeItem(){
        super(new Item.Properties().stacksTo(1));
    }
}
