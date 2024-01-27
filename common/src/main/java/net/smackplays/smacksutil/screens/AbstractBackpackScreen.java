package net.smackplays.smacksutil.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.menus.AbstractBackpackMenu;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.slots.BackpackSlot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_SCREEN_LOCATION;
import static net.smackplays.smacksutil.Constants.MOD_ID;


public class AbstractBackpackScreen<T extends AbstractBackpackMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, C_BACKPACK_SCREEN_LOCATION);
    //A path to the gui texture. In this example we use the texture from the dispenser
    protected final int backgroundWidth = 196;
    protected final int backgroundHeight = 220;

    public AbstractBackpackScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        //in 1.20 or above,this method is in DrawContext class.
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        ItemStack backpack = this.menu.playerInventory.getSelected();
        BackpackInventory inv = (BackpackInventory) this.menu.inventory;
        CompoundTag tag = backpack.getOrCreateTagElement("backpack");
        ListTag listTag = tag.getList("Items", 10);
        Map<Integer, ItemStack> corrList = new HashMap<>();
        for ( int i = 0; i < listTag.size(); i++){
            CompoundTag cTag = listTag.getCompound(i);
            int slot = cTag.getByte("Slot") & 255;
            corrList.putIfAbsent(slot, stackOf(cTag));
        }
        inv.loadAllItems(tag, inv.getItems());
        for (int i = 4; i < inv.getItems().size(); i++){
            inv.setItem(i, corrList.getOrDefault(i, ItemStack.EMPTY));
        }

        BackpackGuiGraphics c = new BackpackGuiGraphics(context, this.minecraft);
        super.render(c, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics context, int mouseX, int mouseY) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem() && this.hoveredSlot instanceof BackpackSlot) {
            ItemStack hoveredStack = this.hoveredSlot.getItem();
            List<Component> list = this.getTooltipFromContainerItem(hoveredStack);
            list.add(1, Component.literal("Count: " + hoveredStack.getCount() + "/" + this.menu.inventory.getMaxStackSize())
                    .withColor(Constants.DARK_GRAY));
            Optional<TooltipComponent> optional = hoveredStack.getTooltipImage();
            context.renderTooltip(this.font, list, optional, mouseX, mouseY);
        } else if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()){
            super.renderTooltip(context, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 76, this.titleLabelY - 27, 0x404040, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 10, this.inventoryLabelY + 29, 0x404040, false);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // Center the title
        titleLabelX = (backgroundWidth - font.width(title)) / 2;
        Button buttonWidget = Button.builder(Component.literal("S"), (bW) -> onButtonWidgetPressed()).pos(x + 156, y + 4).size(12, 12).build();
        this.addRenderableWidget(buttonWidget);
    }

    public void onButtonWidgetPressed() {
        ItemStack stack = this.menu.playerInventory.getSelected();
        if (Services.C2S_PACKET_SENDER != null) {
            Services.C2S_PACKET_SENDER.BackpackSortPacket(stack);
        }
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double) (width - backgroundWidth) / 2 - 10
                || mouseX > (double) (width + backgroundWidth) / 2 + 10
                || mouseY < (double) (height - backgroundHeight) / 2 - 10
                || mouseY > (double) (height + backgroundHeight) / 2 + 10;
    }

    private ItemStack stackOf(CompoundTag tag){
        Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(tag.getString("id")));
        ItemStack stack = new ItemStack(item);
        stack.setCount((int) tag.getFloat("Count"));
        if (tag.contains("tag", 10)) {
            stack.setTag(tag.getCompound("tag").copy());
            if (stack.getTag() != null) {
                stack.getItem().verifyTagAfterLoad(stack.getTag());
            }
        }

        if (stack.getItem().canBeDepleted()) {
            stack.setDamageValue(stack.getDamageValue());
        }
        return stack;
    }
}