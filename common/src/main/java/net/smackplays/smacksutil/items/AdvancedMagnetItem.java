package net.smackplays.smacksutil.items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdvancedMagnetItem extends MagnetItem {

    public AdvancedMagnetItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public void notifyPlayer(Player player, String msg, int color){
        player.displayClientMessage(Component.literal("Advanced Magnet: " + msg).withColor(color), true);
        player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
    }

    @Override
    public int getRange() {
        return 10;
    }
}
