package net.smackplays.smacksutil.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.menus.AbstractEnchantingToolMenu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;


public abstract class AbstractEnchantingToolScreen<T extends AbstractEnchantingToolMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE =
            new ResourceLocation(Constants.MOD_ID, "textures/gui/container/enchantment_slot_highlighted.png");
    private static final ResourceLocation ENCHANTMENT_SLOT_SPRITE =
            new ResourceLocation(Constants.MOD_ID, "textures/gui/container/enchantment_slot.png");
    private static final ResourceLocation ENCHANTMENT_SLOT_DISABLED_SPRITE =
            new ResourceLocation(Constants.MOD_ID, "textures/gui/container/enchantment_slot_disabled.png");
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Constants.MOD_ID, "textures/gui/container/enchanting_tool.png");
    private static final ResourceLocation SCROLLER_SPRITE =
            new ResourceLocation("container/loom/scroller");
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE =
            new ResourceLocation("container/loom/scroller_disabled");
    public boolean scrolling;
    //A path to the gui texture. In this example we use the texture from the dispenser
    protected int backgroundWidth = 176;
    protected int backgroundHeight = 224;
    private float scrollOffs;

    public AbstractEnchantingToolScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        Slot enchantSlot = this.menu.slots.get(0);
        ItemStack stack = enchantSlot.getItem();
        ArrayList<Enchantment> list = new ArrayList<>();
        if (enchantSlot.hasItem()) {
            for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
                if (enchantment.category.canEnchant(stack.getItem()) && lvl == 0) list.add(enchantment);
            }
        }
        ResourceLocation scroller = list.size() > 6 ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        context.blitSprite(scroller, x + 137, (y + 15) + (int) this.scrollOffs, 12, 15);
        if (this.scrollOffs > 0 && enchantSlot.hasItem()) {
            int slice = list.size() - 6;
            float steps = (float) 99 / slice;
            float offset = steps - 1;
            while (this.scrollOffs > offset) {
                list.remove(0);
                offset += steps;
            }
        } else {
            this.scrollOffs = 0;
        }
        for (int i = 0; i < 6; i++) {
            if (enchantSlot.hasItem() && list.size() > i) {
                Enchantment ench = list.get(i);
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(ENCHANTMENT_SLOT_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                }
                ArrayList<Component> comp = new ArrayList<>();
                BuiltInRegistries.ENCHANTMENT
                        .getOptional(EnchantmentHelper.getEnchantmentId(ench))
                        .ifPresent(e -> comp.add(e.getFullname(e.getMaxLevel())));

                context.drawString(this.font, comp.get(0), x + 10, y + 20 + 19 * i, 0x404040, false);
            } else {
                context.blit(ENCHANTMENT_SLOT_DISABLED_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 41, this.titleLabelY - 31, 0x404040, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 1, this.inventoryLabelY + 30, 0x404040, false);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleLabelX = (backgroundWidth - font.width(title)) / 2;
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) (width - backgroundWidth) / 2 - 10
                || mouseX > (double) (width + backgroundWidth) / 2 + 10
                || mouseY < (double) (height - backgroundHeight) / 2 - 10
                || mouseY > (double) (height + backgroundHeight) / 2 + 10;
    }

    public boolean insideScrollbar(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 136 < mouseX;
        boolean b2 = x + 148 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 127 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int $$2, double scrollX, double scrollY) {
        if (this.scrolling) {
            this.scrollOffs = (float) mouseY - 30;
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 99F);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, $$2, scrollX, scrollY);
        }
    }

    @Override
    public boolean mouseReleased(double d, double e, int i) {
        this.scrolling = false;
        return super.mouseReleased(d, e, i);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int $$2) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;

        Slot enchantSlot = this.menu.slots.get(0);

        ItemStack stack = enchantSlot.getItem().copy();
        if (!stack.isEmpty()) {
            ArrayList<Enchantment> list = new ArrayList<>();
            if (enchantSlot.hasItem()) {
                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                    int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
                    if (enchantment.category.canEnchant(stack.getItem()) && lvl == 0) list.add(enchantment);
                }
            }
            int list_size = Math.min(list.size(), 6);
            for (int i = 0; i < list_size; ++i) {
                boolean b1 = x + 6.5 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 14 + 19 * i < mouseY;
                boolean b4 = y + 33 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    Enchantment enchantment = list.get(i);
                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);
                    map.putIfAbsent(enchantment, enchantment.getMaxLevel());
                    EnchantmentHelper.setEnchantments(map, stack);
                    enchantSlot.setChanged();
                    this.menu.setItem(0, 0, stack);
                    assert Minecraft.getInstance().gameMode != null;
                    Minecraft.getInstance().gameMode.handleSlotStateChanged(0, menu.containerId, false);
                    sendPacket(stack);
                    return true;
                }
            }
            if (list.size() > 6 && insideScrollbar(x, y, mouseX, mouseY)) {
                this.scrolling = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, $$2);
    }

    public void sendPacket(ItemStack stack) {

    }

    // 283 126 0 -1
    //284 127 0 1
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double $$2, double scroll_delta) {
        if (!this.scrolling) {
            Slot enchantSlot = this.menu.slots.get(0);
            ItemStack stack = enchantSlot.getItem().copy();
            ArrayList<Enchantment> list = new ArrayList<>();
            if (enchantSlot.hasItem()) {
                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                    int lvl = EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
                    if (enchantment.category.canEnchant(stack.getItem()) && lvl == 0) list.add(enchantment);
                }
            }
            if (list.size() > 6) {
                int slice = list.size() - 6;
                float steps = (float) 99 / slice;

                this.scrollOffs -= (float) scroll_delta * steps;
                if (this.scrollOffs > 99) this.scrollOffs = 99;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, $$2, scroll_delta);
    }
}