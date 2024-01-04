package net.smackplays.smacksutil.mixin.fastplace;


import net.minecraft.client.Minecraft;
import net.smackplays.smacksutil.platform.Services;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Shadow
    private int rightClickDelay;

    @Inject(at = @At("HEAD"), method = "handleKeybinds")
    private void handleKeybinds(CallbackInfo info) {
        // This code is injected into the start of MinecraftServer.loadWorld()V
        if (Services.CONFIG.isEnabledFastPlace()) {
            rightClickDelay = 0;
        }
    }
}
