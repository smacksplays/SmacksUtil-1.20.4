package net.smackplays.smacksutil.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class BackpackGuiGraphics extends GuiGraphics {
    private final Minecraft minecraft;

    public BackpackGuiGraphics(GuiGraphics context, Minecraft client) {
        super(client, context.bufferSource());
        minecraft = client;
    }

    @Override
    public void renderItemDecorations(@NotNull Font font, ItemStack stack, int offsetX, int offsetY, @Nullable String yellowString) {
        if (!stack.isEmpty()) {
            pose().pushPose();
            if (stack.getCount() != 1 || yellowString != null) {
                String countString = yellowString == null ? getCorrCountString(stack) : yellowString;
                pose().translate(0.0F, 0.0F, 200.0F);
                this.drawString(font, countString, offsetX + 19 - 2 - font.width(countString), offsetY + 6 + 3, 16777215, false);
            }

            if (stack.isBarVisible()) {
                int $$6 = stack.getBarWidth();
                int $$7 = stack.getBarColor();
                int $$8 = offsetX + 2;
                int $$9 = offsetY + 13;
                this.fill(RenderType.guiOverlay(), $$8, $$9, $$8 + 13, $$9 + 2, -16777216);
                this.fill(RenderType.guiOverlay(), $$8, $$9, $$8 + $$6, $$9 + 1, $$7 | 0xFF000000);
            }

            LocalPlayer $$10 = minecraft.player;
            float $$11 = $$10 == null ? 0.0F : $$10.getCooldowns().getCooldownPercent(stack.getItem(), minecraft.getFrameTime());
            if ($$11 > 0.0F) {
                int $$12 = offsetY + Mth.floor(16.0F * (1.0F - $$11));
                int $$13 = $$12 + Mth.ceil(16.0F * $$11);
                this.fill(RenderType.guiOverlay(), offsetX, $$12, offsetX + 16, $$13, Integer.MAX_VALUE);
            }

            pose().popPose();
        }
        //super.renderItemDecorations($$0, $$1, $$2, $$3, $$4);
    }

    private static String getCorrCountString(ItemStack stack) {
        String corrCount = String.valueOf(stack.getCount());
        if (stack.getCount() > 999 && stack.getCount() < 1000000) {
            int corr = stack.getCount()/1000;
            corrCount = corr + "k";
        } else if (stack.getCount() > 999999 && stack.getCount() < 1000000000){
            int corr = stack.getCount()/1000;
            corrCount = corr + "M";
        }
        return corrCount;
    }
}
