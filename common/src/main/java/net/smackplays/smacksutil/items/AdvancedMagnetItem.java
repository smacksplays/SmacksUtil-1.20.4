package net.smackplays.smacksutil.items;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdvancedMagnetItem extends MagnetItem {

    public AdvancedMagnetItem(Properties $$0) {
        super($$0);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entity, int $$3, boolean $$4) {
        if (world.isClientSide) return;
        if (!isEnable_magnet(stack)) return;
        AABB area = new AABB(entity.position().add(-10, -10, -10), entity.position().add(10, 10, 10));
        Player player = (Player) entity;
        List<ItemEntity> entities = world.getEntitiesOfClass(ItemEntity.class, area);
        for (ItemEntity e : entities) {
            e.setPos(player.position());
            e.setPickUpDelay(0);
        }

        List<ExperienceOrb> orbs = world.getEntitiesOfClass(ExperienceOrb.class, area);
        for (ExperienceOrb e : orbs) {
            player.takeXpDelay = 0;
            e.playerTouch(player);
        }

        super.inventoryTick(stack, world, entity, $$3, $$4);
    }
}
