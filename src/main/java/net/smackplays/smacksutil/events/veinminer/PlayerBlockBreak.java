package net.smackplays.smacksutil.events.veinminer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.events.KeyInputHandler;
import org.jetbrains.annotations.Nullable;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if (!KeyInputHandler.veinKey.isPressed() || SmacksUtil.veinMiner.isDrawing() || SmacksUtil.veinMiner.isMining()) {
            SmacksUtil.veinMiner.isMining = false;
            return true;
        }
        SmacksUtil.veinMiner.veinMiner(world, player, pos);
        return false;
    }
}
