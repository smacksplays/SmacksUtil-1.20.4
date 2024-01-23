package net.smackplays.smacksutil.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.menus.AbstractTeleportationTabletMenu;
import net.smackplays.smacksutil.platform.Services;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.smackplays.smacksutil.Constants.*;

public class AbstractTeleportationTabletScreen<T extends AbstractTeleportationTabletMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, C_TELEPORTATION_TABLET_SCREEN_LOCATION);
    private static final ResourceLocation ENCHANTING_SLOT_HIGHLIGHTED_SPRITE =
            new ResourceLocation(MOD_ID, C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION);
    private static final ResourceLocation ENCHANTING_SLOT_SPRITE =
            new ResourceLocation(MOD_ID, C_ENCHANTING_SLOT_SPRITE_LOCATION);
    private static final ResourceLocation ENCHANTING_SLOT_DISABLED_SPRITE =
            new ResourceLocation(MOD_ID, C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION);
    private static final ResourceLocation SCROLLER_SPRITE =
            new ResourceLocation(C_SCROLLER_SPRITE_LOCATION);
    private static final ResourceLocation SCROLLER_DISABLED_SPRITE =
            new ResourceLocation(C_SCROLLER_DISABLED_SPRITE_LOCATION);
    public boolean scrolling;
    private float scrollOffs;
    protected final int backgroundWidth = 157;
    protected final int backgroundHeight = 295;
    private final int scrollbarHeight = 175;
    public EditBox editBoxX;
    public EditBox editBoxY;
    public EditBox editBoxZ;
    public EditBox editBoxName;
    public Button removeButtonWidget;
    public boolean isRemove = false;
    private final List<Label> labelList = new ArrayList<>();

    public AbstractTeleportationTabletScreen(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        labelList.clear();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.0F);
        //in 1.20 or above,this method is in DrawContext class.
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.blit(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 512);
        Player player = this.menu.playerInventory.player;
        ItemStack telTool = player.getInventory().getSelected();
        Map<String, TeleportationData> posMap = getTeleportationList(telTool);
        ResourceLocation scroller = posMap.size() > 10 ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
        context.blitSprite(scroller, x + 137, (y + 15) + (int) this.scrollOffs, 12, 15);
        List<String> keyList = new ArrayList<>(posMap.keySet().stream().toList());

        if (this.scrollOffs > 0 && posMap.size() > 10) {
            int slice = posMap.size() - 10;
            float steps = (float) scrollbarHeight / slice;
            float offset = steps - 1;
            while (this.scrollOffs > offset) {
                posMap.remove(keyList.get(0));
                keyList.remove(0);
                offset += steps;
            }
        } else {
            this.scrollOffs = 0;
        }
        for (int i = 0; i < 10; i++) {
            if (posMap.size() > i) {
                String name = keyList.get(i);
                boolean b1 = x + 6 < mouseX;
                boolean b2 = x + 134 > mouseX;
                boolean b3 = y + 13 + 19 * i < mouseY;
                boolean b4 = y + 32 + 19 * i >= mouseY;
                if (b1 && b2 && b3 && b4) {
                    context.blit(ENCHANTING_SLOT_HIGHLIGHTED_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                } else {
                    context.blit(ENCHANTING_SLOT_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
                }
                labelList.add(new Label(Component.literal(name), 10, 20 + 19 * i, 0x404040, false));
            } else {
                context.blit(ENCHANTING_SLOT_DISABLED_SPRITE, x + 8, y + 15 + 19 * i, 0, 0, 126, 19, 126, 19);
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
        context.drawString(this.font, this.title, this.titleLabelX + 10, this.titleLabelY - 65, 0x404040, false);
        for (Label l : labelList){
            context.drawString(this.font, l.component, titleLabelX + 2 + l.x, titleLabelY - 70 + l.y, l.color, l.shadow);
        }
    }

    @Override
    protected void init() {
        super.init();
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        Player player = this.menu.playerInventory.player;

        removeButtonWidget = Button.builder(Component.literal("").withColor(GREEN),
                (bW) -> onToggleRemove()).pos(x + 110, y + 3).size(40, 10).build();
        this.addRenderableWidget(removeButtonWidget);


        StringWidget stringWidgetX = new StringWidget(x - 13, y + 208, 50, 18, Component.literal("X:"), this.font);
        this.addRenderableWidget(stringWidgetX);

        editBoxX = new EditBox(this.font, x + 18, y + 208, 132, 18, Component.literal("X Pos"));
        editBoxX.setValue(String.valueOf(player.position().x));
        this.addRenderableWidget(editBoxX);

        StringWidget stringWidgetY = new StringWidget(x - 13, y + 228, 50, 18, Component.literal("Y:"), this.font);
        this.addRenderableWidget(stringWidgetY);

        editBoxY = new EditBox(this.font, x + 18, y + 228, 132, 18, Component.literal("Y Pos"));
        editBoxY.setValue(String.valueOf(player.position().y));
        this.addRenderableWidget(editBoxY);

        StringWidget stringWidgetZ = new StringWidget(x - 13, y + 248, 50, 18, Component.literal("Z:"), this.font);
        this.addRenderableWidget(stringWidgetZ);

        editBoxZ = new EditBox(this.font, x + 18, y + 248, 132, 18, Component.literal("Z Pos"));
        editBoxZ.setValue(String.valueOf(player.position().z));
        this.addRenderableWidget(editBoxZ);

        editBoxName = new EditBox(this.font, x + 18, y + 268, 100, 18, Component.literal("Z Pos"));
        editBoxName.setValue("Name");
        this.addRenderableWidget(editBoxName);

        Button buttonWidget = Button.builder(Component.literal("Add"), (bW) -> onButtonWidgetPressed()).pos(x + 120, y + 268).size(30, 18).build();
        this.addRenderableWidget(buttonWidget);
    }

    private void onButtonWidgetPressed(){
        ItemStack stack = this.menu.playerInventory.player.getInventory().getSelected();
        if (editBoxX.getValue().isBlank() || editBoxY.getValue().isBlank() || editBoxZ.getValue().isBlank() || editBoxName.getValue().isBlank()) return;
        if (NumberUtils.isParsable(editBoxX.getValue())
                && NumberUtils.isParsable(editBoxY.getValue())
                && NumberUtils.isParsable(editBoxZ.getValue())){
            double posX = NumberUtils.createDouble(editBoxX.getValue());
            double posY = NumberUtils.createDouble(editBoxY.getValue());
            double posZ = NumberUtils.createDouble(editBoxZ.getValue());
            String name = editBoxName.getValue();
            Vec3 pos = new Vec3(posX, posY, posZ);
            float xRot = this.menu.playerInventory.player.getXRot();
            float yRot = this.menu.playerInventory.player.getYRot();
            Level level = this.menu.playerInventory.player.level();
            String dim = level.dimensionTypeId().location().getPath();

            Services.C2S_PACKET_SENDER.sendToServerTeleportNBTPacket(stack, pos, xRot, yRot, name, dim, false);
        }
    }

    private void onToggleRemove(){
        this.isRemove = !this.isRemove;
        String text = this.isRemove ? "Remove" : "";
        int color = this.isRemove ? RED : GREEN;
        removeButtonWidget.setMessage(Component.literal(text).withColor(color));
    }

    private Map<String, TeleportationData> getTeleportationList(ItemStack stack){
        Map<String, TeleportationData> map = new HashMap<>();
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("Positions")){
            ListTag listTag = (ListTag) tag.get("Positions");
            if (listTag != null){
                for (Tag value : listTag) {
                    CompoundTag p = (CompoundTag) value;
                    double x = p.getDouble("x_pos");
                    double y = p.getDouble("y_pos");
                    double z = p.getDouble("z_pos");
                    float xRot = p.getFloat("x_rot");
                    float yRot = p.getFloat("y_rot");
                    String name = p.getString("name");
                    String dim = p.getString("dim");
                    ResourceKey<Level> levelKey = Level.OVERWORLD;
                    if (dim.equals("the_nether")){
                        levelKey = Level.NETHER;
                    } else if (dim.equals("the_end")){
                        levelKey = Level.END;
                    }

                    TeleportationData data = new TeleportationData(new Vec3(x, y, z), xRot, yRot, dim, levelKey);
                    map.putIfAbsent(name, data);
                }
            }
        }
        return map;
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int $$2, double scrollX, double scrollY) {
        int y = (height - backgroundHeight) / 2;
        if (this.scrolling) {
            this.scrollOffs = (float) mouseY - y - 22;
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, scrollbarHeight);
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
    public boolean mouseClicked(double mouseX, double mouseY, int lrClick) {
        int x = (width - this.backgroundWidth) / 2;
        int y = (height - this.backgroundHeight) / 2;
        ItemStack stack = this.menu.playerInventory.player.getInventory().getSelected();
        if (lrClick == 0){
            if (!stack.isEmpty()) {
                Map<String, TeleportationData> posMap = getTeleportationList(stack);
                List<String> keyList = posMap.keySet().stream().toList();
                int list_size = Math.min(posMap.size(), 10);
                for (int i = 0; i < list_size; ++i) {
                    boolean b1 = x + 6.5 < mouseX;
                    boolean b2 = x + 134 > mouseX;
                    boolean b3 = y + 14 + 19 * i < mouseY;
                    boolean b4 = y + 33 + 19 * i >= mouseY;
                    if (b1 && b2 && b3 && b4) {
                        String name = keyList.get(i);
                        if (isRemove){
                            Services.C2S_PACKET_SENDER.sendToServerTeleportNBTPacket(stack, posMap.get(name).pos, posMap.get(name).xRot, posMap.get(name).yRot, name, posMap.get(name).dim, isRemove);
                        } else {
                            Services.C2S_PACKET_SENDER.sendToServerTeleportPacket(posMap.get(name).levelKey, posMap.get(name).pos, posMap.get(name).xRot, posMap.get(name).yRot);
                        }
                        return true;
                    }
                }
                if (posMap.size() > 10 && insideScrollbar(x, y, mouseX, mouseY)) {
                    this.scrolling = true;
                }
            }
        } else {
            if (insideEditBoxX(x, y, mouseX, mouseY)){
                editBoxX.setValue("");
            } else if (insideEditBoxY(x, y, mouseX, mouseY)){
                editBoxY.setValue("");
            } else if (insideEditBoxZ(x, y, mouseX, mouseY)){
                editBoxZ.setValue("");
            } else if (insideEditBoxName(x, y, mouseX, mouseY)){
                editBoxName.setValue("");
            }
        }
        return super.mouseClicked(mouseX, mouseY, lrClick);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double $$2, double scroll_delta) {
        if (!this.scrolling) {
            Player player = this.menu.playerInventory.player;
            ItemStack telTool = player.getInventory().getSelected();
            Map<String, TeleportationData> posMap = getTeleportationList(telTool);
            if (posMap.size() > 10) {
                int slice = posMap.size() - 10;
                float steps = (float) scrollbarHeight / slice;

                this.scrollOffs -= (float) scroll_delta * steps;
                if (this.scrollOffs < 0) this.scrollOffs = 0;
                else if (this.scrollOffs > scrollbarHeight) this.scrollOffs = scrollbarHeight;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, $$2, scroll_delta);
    }

    public boolean insideScrollbar(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 136 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 13 < mouseY;
        boolean b4 = y + 205 >= mouseY;
        return b1 && b2 && b3 && b4;
    }
    public boolean insideEditBoxX(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 208 < mouseY;
        boolean b4 = y + 226 >= mouseY;
        return b1 && b2 && b3 && b4;
    }
    public boolean insideEditBoxY(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 228 < mouseY;
        boolean b4 = y + 246 >= mouseY;
        return b1 && b2 && b3 && b4;
    }
    public boolean insideEditBoxZ(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 148.5 >= mouseX;
        boolean b3 = y + 248 < mouseY;
        boolean b4 = y + 266 >= mouseY;
        return b1 && b2 && b3 && b4;
    }
    public boolean insideEditBoxName(int x, int y, double mouseX, double mouseY) {
        boolean b1 = x + 18 < mouseX;
        boolean b2 = x + 116.5 >= mouseX;
        boolean b3 = y + 268 < mouseY;
        boolean b4 = y + 286 >= mouseY;
        return b1 && b2 && b3 && b4;
    }

    private record Label(Component component, int x, int y, int color, boolean shadow) {
    }

    private record TeleportationData(Vec3 pos, float xRot, float yRot, String dim, ResourceKey<Level> levelKey) {
    }
}

