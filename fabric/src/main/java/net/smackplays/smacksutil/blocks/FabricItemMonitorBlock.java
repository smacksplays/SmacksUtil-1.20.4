package net.smackplays.smacksutil.blocks;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.smackplays.smacksutil.blockEntities.FabricItemMonitorBlockEntity;
import net.smackplays.smacksutil.menus.FabricItemMonitorMenu;
import org.jetbrains.annotations.Nullable;

public class FabricItemMonitorBlock extends AbstractItemMonitorBlock {
    public static final MapCodec<FabricItemMonitorBlock> CODEC = simpleCodec(FabricItemMonitorBlock::new);

    public FabricItemMonitorBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<FabricItemMonitorBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FabricItemMonitorBlockEntity(blockPos, blockState);
    }

    @Override
    protected void openContainer(Level world, BlockPos pos, Player player) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof FabricItemMonitorBlockEntity) {
            player.openMenu(createScreenHandlerFactory(entity));
        }
    }

    public MenuProvider createScreenHandlerFactory(BlockEntity entity) {
        return new ExtendedScreenHandlerFactory() {
            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
            }

            @Override
            public Component getDisplayName() {
                return entity.getBlockState().getBlock().getName();
            }

            @Override
            public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                return new FabricItemMonitorMenu(syncId, playerInventory, (Container) entity, ContainerLevelAccess.create(entity.getLevel(), entity.getBlockPos()));
            }
        };
    }
}
