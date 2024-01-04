package net.smackplays.smacksutil.mixin.fasteat;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getUseDuration", cancellable = true)
    private void getMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int mxa = Services.CONFIG.getMaxRenderBlocks();
        if (stack.getItem().isEdible() && Services.CONFIG.isEnabledFastEat()) {
            cir.setReturnValue(4);
        }
    }
}
