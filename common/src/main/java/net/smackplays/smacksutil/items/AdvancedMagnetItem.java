package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class AdvancedMagnetItem extends MagnetItem {

    public AdvancedMagnetItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Advanced Magnet: " + msg).withColor(color), true);
    }

    @Override
    public int getRange() {
        return 10;
    }
}
