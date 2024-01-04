package net.smackplays.smacksutil.mixin.veinminer;

import net.minecraft.client.MouseHandler;
import net.smackplays.smacksutil.SmacksUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MouseHandler.class)
public class MouseScroll {
    @Inject(at = @At("HEAD"), method = "onScroll")
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        SmacksUtil.veinMiner.scroll(vertical);
    }
}
