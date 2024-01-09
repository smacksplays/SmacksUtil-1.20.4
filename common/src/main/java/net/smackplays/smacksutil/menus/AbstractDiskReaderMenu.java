package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AbstractDiskReaderMenu extends AbstractContainerMenu {
    protected AbstractDiskReaderMenu(@Nullable MenuType<?> $$0, int $$1) {
        super($$0, $$1);
    }

    @Override
    public ItemStack quickMoveStack(Player var1, int var2) {
        return null;
    }

    @Override
    public boolean stillValid(Player var1) {
        return false;
    }
}
