package net.smackplays.smacksutil.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.items.AdvancedMagnetItem;
import net.smackplays.smacksutil.items.AutoLightWandItem;
import net.smackplays.smacksutil.items.MagnetItem;

public class SmacksTrinket implements Trinket {
    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (stack.getItem() instanceof MagnetItem magnet){
            magnet.inventoryTick(stack, entity.level(), entity, 0, true);
        } else if (stack.getItem() instanceof AdvancedMagnetItem magnet){
            magnet.inventoryTick(stack, entity.level(), entity, 0, true);
        } else if (stack.getItem() instanceof AutoLightWandItem lightWandItem){
            lightWandItem.inventoryTick(stack, entity.level(), entity, 0, true);
        }
    }
}
