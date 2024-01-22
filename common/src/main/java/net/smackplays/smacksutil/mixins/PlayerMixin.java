package net.smackplays.smacksutil.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(at = @At("HEAD"), method = "interactOn")
    private void onScroll(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = (Player) (Object) this;
        ItemStack mainHand = player.getItemInHand(interactionHand);
        Services.CLIENT_PACKET_SENDER.sendToServerInteractEntityPacket(mainHand, entity.getUUID(), interactionHand.equals(InteractionHand.MAIN_HAND));
    }
}