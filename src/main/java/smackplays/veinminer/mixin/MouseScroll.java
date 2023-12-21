package smackplays.veinminer.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import smackplays.veinminer.Miner;


@Mixin(Mouse.class)
public class MouseScroll {
    @Inject(at = @At("HEAD"), method = "onMouseScroll")
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        Miner.scroll(window, horizontal, vertical);
    }
}
