package net.smackplays.smacksutil.menus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import org.jetbrains.annotations.Nullable;

public class TeleportationTabletMenu extends AbstractTeleportationTabletMenu{
    public TeleportationTabletMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    @SuppressWarnings("unused")
    public static TeleportationTabletMenu create(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        return new TeleportationTabletMenu(SmacksUtil.TELEPORTATION_TABLET_MENU.get(), syncId, playerInventory);
    }
}
