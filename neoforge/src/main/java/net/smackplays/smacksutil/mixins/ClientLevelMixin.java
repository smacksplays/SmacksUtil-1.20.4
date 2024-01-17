package net.smackplays.smacksutil.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.smackplays.smacksutil.SmacksUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(at = @At("TAIL"), method = "getMarkerParticleTarget", cancellable = true)
    public void getMarkerParticleTarget(CallbackInfoReturnable<Block> cir) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack stack = player.getMainHandItem();
        if (stack.is(SmacksUtil.LIGHT_WAND_ITEM.get()) || stack.is(SmacksUtil.AUTO_LIGHT_WAND_ITEM.get())) {
            BlockItem blockItem = (BlockItem) Items.LIGHT;
            cir.setReturnValue(blockItem.getBlock());
        }
    }
}
