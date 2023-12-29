package net.smackplays.smacksutil.backpacks;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.smackplays.smacksutil.SmacksUtil;

public class LargeBackpackScreen extends HandledScreen<LargeBackpackScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(SmacksUtil.MOD_ID, "textures/gui/container/large_backpack.png");
    //A path to the gui texture. In this example we use the texture from the dispenser
    protected int backgroundWidth = 248;
    protected int backgroundHeight = 281;

    public LargeBackpackScreen(LargeBackpackScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 512);
        //in 1.20 or above,this method is in DrawContext class.
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX - 112, this.titleY - 56, 0x404040, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX - 36, this.playerInventoryTitleY + 53, 0x404040, false);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) (width - backgroundWidth) / 2 - 10
                || mouseX > (double) (width + backgroundWidth) / 2 + 10
                || mouseY < (double) (height - backgroundHeight) / 2 - 10
                || mouseY > (double) (height + backgroundHeight) / 2 + 10;
    }

    /*
    boolean c1 = mouseX < 360;
        double comp1 = width / 2 - backgroundWidth / 2;
        boolean c11 = mouseX > x1 - 10;
        boolean c2 = mouseX > 600;
        double comp2 = width / 2 - backgroundWidth / 2;
        boolean c22 = mouseX < x2 + 10;
        boolean c3 = mouseY < 128;
        boolean c4 = mouseY > 400;
     */
}