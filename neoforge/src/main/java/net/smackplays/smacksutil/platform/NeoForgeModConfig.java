package net.smackplays.smacksutil.platform;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigManager;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.services.IModConfig;

@SuppressWarnings({"unused", "rawtypes", "UnstableApiUsage"})
@Config(name = Constants.MOD_ID)
public class NeoForgeModConfig implements IModConfig, ConfigData {
    @ConfigEntry.Gui.Excluded
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    @ConfigEntry.Gui.Excluded
    public static NeoForgeModConfig INSTANCE;
    private static ConfigManager MANAGER;
    public int maxRenderBlocks = IModConfig.maxRenderBlocks;
    public int maxShapelessRadius = IModConfig.maxShapelessRadius;
    public int maxRenderShapelessRadius = IModConfig.maxRenderShapelessRadius;
    public boolean enabledShapelessVerticalMode = IModConfig.enabledShapelessVerticalMode;
    public int maxShapelessVerticalRadius = IModConfig.maxShapelessVerticalRadius;
    public int maxRenderShapelessVerticalRadius = IModConfig.maxRenderShapelessVerticalRadius;
    public boolean enabledTunnelMode = IModConfig.enabledTunnelMode;
    public boolean enabledMineshaftMode = IModConfig.enabledMineshaftMode;
    public boolean enabledVegetationMode = IModConfig.enabledVegetationMode;
    public boolean enabledOresMode = IModConfig.enabledOresMode;
    public boolean enabledCropsMode = IModConfig.enabledCropsMode;
    public boolean enabledTreesMode = IModConfig.enabledTreesMode;
    public boolean enabledFastEat = IModConfig.enabledFastEat;
    public boolean enabledFastPlace = IModConfig.enabledFastPlace;

    public static ConfigBuilder create() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(Component.translatable("smacksutil.configsceren.title"));
        builder.setGlobalized(true);
        builder.setGlobalizedExpanded(false);
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory config = builder.getOrCreateCategory(Component.translatable("smacksutil.category.config"));
        config.addEntry(entryBuilder
                .startIntField(Component.translatable("text.autoconfig.smacksutil.option.maxRenderBlocks")
                        , Services.CONFIG.getMaxRenderBlocks())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.maxRenderBlocks.@Tooltip"))
                .setDefaultValue(150)
                .setSaveConsumer(Services.CONFIG::setMaxRenderBlocks)
                .build());

        config.addEntry(entryBuilder
                .startIntField(
                        Component.translatable("text.autoconfig.smacksutil.option.maxShapelessRadius")
                        , Services.CONFIG.getMaxShapelessRadius())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.maxShapelessRadius.@Tooltip"))
                .setDefaultValue(6)
                .setSaveConsumer(Services.CONFIG::setMaxShapelessRadius)
                .build());

        config.addEntry(entryBuilder
                .startIntField(
                        Component.translatable("text.autoconfig.smacksutil.option.maxRenderShapelessRadius")
                        , Services.CONFIG.getMaxRenderShapelessRadius())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.maxRenderShapelessRadius.@Tooltip"))
                .setDefaultValue(3)
                .setSaveConsumer(Services.CONFIG::setMaxRenderShapelessRadius)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledShapelessVerticalMode")
                        , Services.CONFIG.isEnabledShapelessVerticalMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledShapelessVerticalMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledShapelessVerticalMode)
                .build());

        config.addEntry(entryBuilder
                .startIntField(
                        Component.translatable("text.autoconfig.smacksutil.option.maxShapelessVerticalRadius")
                        , Services.CONFIG.getMaxShapelessVerticalRadius())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.maxShapelessVerticalRadius.@Tooltip"))
                .setDefaultValue(6)
                .setSaveConsumer(Services.CONFIG::setMaxShapelessVerticalRadius)
                .build());

        config.addEntry(entryBuilder
                .startIntField(
                        Component.translatable("text.autoconfig.smacksutil.option.maxRenderShapelessVerticalRadius")
                        , Services.CONFIG.getMaxRenderShapelessVerticalRadius())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.maxRenderShapelessVerticalRadius.@Tooltip"))
                .setDefaultValue(3)
                .setSaveConsumer(Services.CONFIG::setMaxRenderShapelessVerticalRadius)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledTunnelMode")
                        , Services.CONFIG.isEnabledTunnelMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledTunnelMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledTunnelMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledMineshaftMode")
                        , Services.CONFIG.isEnabledMineshaftMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledMineshaftMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledMineshaftMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledVegetationMode")
                        , Services.CONFIG.isEnabledVegetationMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledVegetationMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledVegetationMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledOresMode")
                        , Services.CONFIG.isEnabledOresMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledOresMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledOresMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledCropsMode")
                        , Services.CONFIG.isEnabledCropsMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledCropsMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledCropsMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledTreesMode")
                        , Services.CONFIG.isEnabledTreesMode())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledTreesMode.@Tooltip"))
                .setDefaultValue(true)
                .setSaveConsumer(Services.CONFIG::setEnabledTreesMode)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledFastEat")
                        , INSTANCE.isEnabledFastPlace())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledFastEat.@Tooltip"))
                .setDefaultValue(false)
                .setSaveConsumer(Services.CONFIG::setEnabledFastEat)
                .build());

        config.addEntry(entryBuilder
                .startBooleanToggle(
                        Component.translatable("text.autoconfig.smacksutil.option.enabledFastPlace")
                        , Services.CONFIG.isEnabledFastPlace())
                .setTooltip(Component.translatable("text.autoconfig.smacksutil.option.enabledFastPlace.@Tooltip"))
                .setDefaultValue(false)
                .setSaveConsumer(Services.CONFIG::setEnabledFastPlace)
                .build());
        return builder;
    }

    @Override
    public int getMaxRenderBlocks() {
        return INSTANCE.maxRenderBlocks;
    }

    @Override
    public void setMaxRenderBlocks(int toSet) {
        INSTANCE.maxRenderBlocks = toSet;
        MANAGER.save();
    }

    @Override
    public int getMaxShapelessRadius() {
        return INSTANCE.maxShapelessRadius;
    }

    @Override
    public void setMaxShapelessRadius(int toSet) {
        INSTANCE.maxShapelessRadius = toSet;
        MANAGER.save();
    }

    @Override
    public int getMaxRenderShapelessRadius() {
        return INSTANCE.maxRenderShapelessRadius;
    }

    @Override
    public void setMaxRenderShapelessRadius(int toSet) {
        INSTANCE.maxRenderShapelessRadius = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledShapelessVerticalMode() {
        return INSTANCE.enabledShapelessVerticalMode;
    }

    @Override
    public void setEnabledShapelessVerticalMode(boolean toSet) {
        INSTANCE.enabledShapelessVerticalMode = toSet;
        MANAGER.save();
    }

    @Override
    public int getMaxShapelessVerticalRadius() {
        return INSTANCE.maxShapelessVerticalRadius;
    }

    @Override
    public void setMaxShapelessVerticalRadius(int toSet) {
        INSTANCE.maxShapelessVerticalRadius = toSet;
        MANAGER.save();
    }

    @Override
    public int getMaxRenderShapelessVerticalRadius() {
        return INSTANCE.maxRenderShapelessVerticalRadius;
    }

    @Override
    public void setMaxRenderShapelessVerticalRadius(int toSet) {
        INSTANCE.maxRenderShapelessVerticalRadius = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledTunnelMode() {
        return INSTANCE.enabledTunnelMode;
    }

    @Override
    public void setEnabledTunnelMode(boolean toSet) {
        INSTANCE.enabledTunnelMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledMineshaftMode() {
        return INSTANCE.enabledMineshaftMode;
    }

    @Override
    public void setEnabledMineshaftMode(boolean toSet) {
        INSTANCE.enabledMineshaftMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledVegetationMode() {
        return INSTANCE.enabledVegetationMode;
    }

    @Override
    public void setEnabledVegetationMode(boolean toSet) {
        INSTANCE.enabledVegetationMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledOresMode() {
        return INSTANCE.enabledOresMode;
    }

    @Override
    public void setEnabledOresMode(boolean toSet) {
        INSTANCE.enabledOresMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledCropsMode() {
        return INSTANCE.enabledCropsMode;
    }

    @Override
    public void setEnabledCropsMode(boolean toSet) {
        INSTANCE.enabledCropsMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledTreesMode() {
        return INSTANCE.enabledTreesMode;
    }

    @Override
    public void setEnabledTreesMode(boolean toSet) {
        INSTANCE.enabledTreesMode = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledFastEat() {
        return INSTANCE.enabledFastEat;
    }

    @Override
    public void setEnabledFastEat(boolean toSet) {
        INSTANCE.enabledFastEat = toSet;
        MANAGER.save();
    }

    @Override
    public boolean isEnabledFastPlace() {
        return INSTANCE.enabledFastPlace;
    }

    @Override
    public void setEnabledFastPlace(boolean toSet) {
        INSTANCE.enabledFastPlace = toSet;
        MANAGER.save();
    }

    public void init() {
        AutoConfig.register(NeoForgeModConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(NeoForgeModConfig.class).getConfig();
        MANAGER = (ConfigManager) AutoConfig.getConfigHolder(NeoForgeModConfig.class);
    }
}
