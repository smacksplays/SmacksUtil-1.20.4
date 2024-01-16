package net.smackplays.smacksutil.mixins;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.smackplays.smacksutil.items.AdvancedMobImpTool;
import net.smackplays.smacksutil.items.MobImprisonmentTool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(at = @At("HEAD"), method = "interactOn")
    private void onScroll(Entity entity, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = entity.level();
        if (world.isClientSide) return;
        Player player = (Player) (Object) this;
        ItemStack mainHand = player.getItemInHand(interactionHand);
        if (mainHand.getItem() instanceof MobImprisonmentTool mobTool) {
            mobTool.interactLivingEntity(mainHand, player, (LivingEntity) entity, interactionHand);
        }
        if (mainHand.getItem() instanceof AdvancedMobImpTool mobTool) {
            mobTool.interactLivingEntity(mainHand, player, (LivingEntity) entity, interactionHand);
        }
    }
}