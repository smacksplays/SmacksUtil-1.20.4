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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.inventories.LargeBackpackInventory;
import net.smackplays.smacksutil.menus.AbstractLargeBackpackMenu;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static net.smackplays.smacksutil.Constants.C_LARGE_BACKPACK_SCREEN_LOCATION;
import static net.smackplays.smacksutil.Constants.MOD_ID;


public class AbstractLargeBackpackScreen<T extends AbstractLargeBackpackMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, C_LARGE_BACKPACK_SCREEN_LOCATION);
    //A path to the gui texture. In this example we use the texture from the dispenser
    protected final int backgroundWidth = 268;
    protected final int backgroundHeight = 275;

    public AbstractLargeBackpackScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 512);
        //in 1.20 or above,this method is in DrawContext class.
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);

        ItemStack backpack = this.menu.playerInventory.getSelected();
        LargeBackpackInventory inv = (LargeBackpackInventory) this.menu.inventory;
        CompoundTag tag = backpack.getOrCreateTagElement("large_backpack");
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


        BackpackGuiGraphics c = new BackpackGuiGraphics(context, this.minecraft, this.menu.playerInventory);
        super.render(c, mouseX, mouseY, delta);
        renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, this.titleLabelX - 130, this.titleLabelY - 54, 0x404040, false);
        context.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX - 36, this.inventoryLabelY + 53, 0x404040, false);
        context.drawString(this.font, Component.literal("Max: " + this.menu.inventory.getMaxStackSize()), this.titleLabelX + 25, this.titleLabelY - 54, 0x00c224, false);
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // Center the title
        titleLabelX = (backgroundWidth - font.width(title)) / 2;
        Button buttonWidget1 = Button.builder(Component.literal("S"), (buttonWidget) -> onButtonWidgetPressed()).pos(x + 228, y + 4).size(12, 12).build();
        this.addRenderableWidget(buttonWidget1);
    }

    public void onButtonWidgetPressed() {
        ItemStack stack = this.menu.playerInventory.getSelected();
        Services.C2S_PACKET_SENDER.sendToServerSortPacket(stack);
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