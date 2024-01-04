package net.smackplays.smacksutil.event.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.event.KeyInputHandler;
import org.jetbrains.annotations.Nullable;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!KeyInputHandler.veinKey.isDown() || SmacksUtil.veinMiner.isDrawing() || SmacksUtil.veinMiner.isMining()) {
            SmacksUtil.veinMiner.isMining = false;
            return true;
        }
        SmacksUtil.veinMiner.veinMiner(world, player, pos);
        return false;
    }
}
