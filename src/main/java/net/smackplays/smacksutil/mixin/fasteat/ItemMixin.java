package net.smackplays.smacksutil.mixin.fasteat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.smackplays.smacksutil.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getMaxUseTime", cancellable = true)
    private void getMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem().isFood() && ModConfig.INSTANCE.enableFastEat) {
            cir.setReturnValue(4);
        }
    }
}
