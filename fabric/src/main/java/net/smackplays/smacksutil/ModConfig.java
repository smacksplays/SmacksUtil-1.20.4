package net.smackplays.smacksutil;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = Constants.MOD_ID)
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Excluded
    public static ModConfig INSTANCE;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum blocks that are allowed to be rendered.")
    public int maxRenderBlocks = 150;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxShapelessRadius = 6;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxRenderShapelessRadius = 3;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Shapeless Mode.")
    public boolean enableShapelessVerticalMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxShapelessVerticalRadius = 6;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxRenderShapelessVerticalRadius = 3;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Tunnel Mode.")
    public boolean enableTunnelMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Mineshaft Mode.")
    public boolean enableMineshaftMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Vegetation Mode.")
    public boolean enableVegetationMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Ores Mode.")
    public boolean enableOresMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Crops Mode.")
    public boolean enableCropsMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Trees Mode.")
    public boolean enableTreesMode = true;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Fast eating.")
    public boolean enableFastEat = true;

    public static void init() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
