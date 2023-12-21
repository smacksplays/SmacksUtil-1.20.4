package smackplays.veinminer.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import smackplays.veinminer.Miner;
import smackplays.veinminer.VeinMiner;

import java.util.ArrayList;
import java.util.List;

public class PlayerBlockBreak implements PlayerBlockBreakEvents.Before {

    @Override
    public boolean beforeBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        if(!KeyInputHandler.veinKey.isPressed() || VeinMiner.veinMiner.isDrawing() || VeinMiner.veinMiner.isMining()){
            VeinMiner.veinMiner.isMining = false;
            return true;
        }
        VeinMiner.veinMiner.veinMiner(world, player, pos);
        return false;
    }
}
