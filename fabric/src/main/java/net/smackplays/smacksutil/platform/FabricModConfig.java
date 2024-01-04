package net.smackplays.smacksutil.platform;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.services.IModConfig;

@Config(name = Constants.MOD_ID)
public class FabricModConfig implements IModConfig, ConfigData {
    @ConfigEntry.Gui.Excluded
    public static FabricModConfig INSTANCE;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum blocks that are allowed to be rendered.")
    public int maxRenderBlocks = IModConfig.maxRenderBlocks;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxShapelessRadius = IModConfig.maxShapelessRadius;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in Shapeless mode.")
    public int maxRenderShapelessRadius = IModConfig.maxRenderShapelessRadius;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Shapeless Mode.")
    public boolean enabledShapelessVerticalMode = IModConfig.enabledShapelessVerticalMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxShapelessVerticalRadius = IModConfig.maxShapelessVerticalRadius;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Maximum radius in ShapelessVertical mode.")
    public int maxRenderShapelessVerticalRadius = IModConfig.maxRenderShapelessVerticalRadius;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Tunnel Mode.")
    public boolean enabledTunnelMode = IModConfig.enabledTunnelMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Mineshaft Mode.")
    public boolean enabledMineshaftMode = IModConfig.enabledMineshaftMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Vegetation Mode.")
    public boolean enabledVegetationMode = IModConfig.enabledVegetationMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Ores Mode.")
    public boolean enabledOresMode = IModConfig.enabledOresMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Crops Mode.")
    public boolean enabledCropsMode = IModConfig.enabledCropsMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Trees Mode.")
    public boolean enabledTreesMode = IModConfig.enabledTreesMode;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Fast eating.")
    public boolean enabledFastEat = IModConfig.enabledFastEat;
    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable Fast Placing.")
    public boolean enabledFastPlace = IModConfig.enabledFastPlace;

    @Override
    public int getMaxRenderBlocks() {
        return INSTANCE.maxRenderBlocks;
    }

    @Override
    public void setMaxRenderBlocks(int toSet) {
        INSTANCE.maxRenderBlocks = toSet;
    }

    @Override
    public int getMaxShapelessRadius() {
        return INSTANCE.maxShapelessRadius;
    }

    @Override
    public void setMaxShapelessRadius(int toSet) {
        INSTANCE.maxShapelessRadius = toSet;
    }

    @Override
    public int getMaxRenderShapelessRadius() {
        return INSTANCE.maxRenderShapelessRadius;
    }

    @Override
    public void setMaxRenderShapelessRadius(int toSet) {
        INSTANCE.maxRenderShapelessRadius = toSet;
    }

    @Override
    public boolean isEnabledShapelessVerticalMode() {
        return INSTANCE.enabledShapelessVerticalMode;
    }

    @Override
    public void setEnabledShapelessVerticalMode(boolean toSet) {
        INSTANCE.enabledShapelessVerticalMode = toSet;
    }

    @Override
    public int getMaxShapelessVerticalRadius() {
        return INSTANCE.maxShapelessVerticalRadius;
    }

    @Override
    public void setMaxShapelessVerticalRadius(int toSet) {
        INSTANCE.maxShapelessVerticalRadius = toSet;
    }

    @Override
    public int getMaxRenderShapelessVerticalRadius() {
        return INSTANCE.maxRenderShapelessVerticalRadius;
    }

    @Override
    public void setMaxRenderShapelessVerticalRadius(int toSet) {
        INSTANCE.maxRenderShapelessVerticalRadius = toSet;
    }

    @Override
    public boolean isEnabledTunnelMode() {
        return INSTANCE.enabledTunnelMode;
    }

    @Override
    public void setEnabledTunnelMode(boolean toSet) {
        INSTANCE.enabledTunnelMode = toSet;
    }

    @Override
    public boolean isEnabledMineshaftMode() {
        return INSTANCE.enabledMineshaftMode;
    }

    @Override
    public void setEnabledMineshaftMode(boolean toSet) {
        INSTANCE.enabledMineshaftMode = toSet;
    }

    @Override
    public boolean isEnabledVegetationMode() {
        return INSTANCE.enabledVegetationMode;
    }

    @Override
    public void setEnabledVegetationMode(boolean toSet) {
        INSTANCE.enabledVegetationMode = toSet;
    }

    @Override
    public boolean isEnabledOresMode() {
        return INSTANCE.enabledOresMode;
    }

    @Override
    public void setEnabledOresMode(boolean toSet) {
        INSTANCE.enabledOresMode = toSet;
    }

    @Override
    public boolean isEnabledCropsMode() {
        return INSTANCE.enabledCropsMode;
    }

    @Override
    public void setEnabledCropsMode(boolean toSet) {
        INSTANCE.enabledCropsMode = toSet;
    }

    @Override
    public boolean isEnabledTreesMode() {
        return INSTANCE.enabledTreesMode;
    }

    @Override
    public void setEnabledTreesMode(boolean toSet) {
        INSTANCE.enabledTreesMode = toSet;
    }

    @Override
    public boolean isEnabledFastEat() {
        return INSTANCE.enabledFastEat;
    }

    @Override
    public void setEnabledFastEat(boolean toSet) {
        INSTANCE.enabledFastEat = toSet;
    }

    @Override
    public boolean isEnabledFastPlace() {
        return INSTANCE.enabledFastPlace;
    }

    @Override
    public void setEnabledFastPlace(boolean toSet) {
        INSTANCE.enabledFastPlace = toSet;
    }


    public void init() {
        AutoConfig.register(FabricModConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(FabricModConfig.class).getConfig();
    }
}
