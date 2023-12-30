package net.smackplays.smacksutil.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class SmacksUtilConfigScreen extends Screen {
    private Screen previous;
    private SimpleOption<Integer> fov;
    protected static final ButtonWidget.NarrationSupplier DEFAULT_NARRATION_SUPPLIER = (textSupplier) -> {
        return (MutableText)textSupplier.get();
    };

    public SmacksUtilConfigScreen(Screen parent) {
        super(Text.translatable("smacksutil.configsceren.title"));
        this.previous = parent;
        ButtonWidget.Builder builder = ButtonWidget.builder(Text.literal("Button"), (press) -> {})
               .size(100,10).position(20,20);
        //this.addDrawableChild(builder.build());

    }

}
