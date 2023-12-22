package net.smackplays.smacksutil.mixin.FastPlace;


import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.smackplays.smacksutil.SmacksUtil;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    private int itemUseCooldown;

    @Inject(at = @At("HEAD"), method = "handleInputEvents")
    private void handleInputEvents(CallbackInfo info) {
        // This code is injected into the start of MinecraftServer.loadWorld()V
        if(SmacksUtil.getFastPlace()){
            itemUseCooldown = 0;
        }
    }
}
