package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.platform.services.IKeyHandler;
import org.jetbrains.annotations.Nullable;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!IKeyHandler.veinKey.isDown() || Services.VEIN_MINER.isDrawing() || Services.VEIN_MINER.isMining()) {
            Services.VEIN_MINER.isMining = false;
            return true;
        }
        Services.VEIN_MINER.veinMiner(world, player, pos);
        return false;
    }
}
