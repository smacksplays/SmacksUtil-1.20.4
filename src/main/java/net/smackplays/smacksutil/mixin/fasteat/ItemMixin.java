package net.smackplays.smacksutil.mixin.fasteat;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(at = @At("HEAD"), method = "getUseDuration", cancellable = true)
    private void getMaxUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.getItem().isEdible() && ModConfig.INSTANCE.enableFastEat) {
            cir.setReturnValue(4);
        }
    }
}
