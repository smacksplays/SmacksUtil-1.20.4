package net.smackplays.smacksutil.platform.services;

public interface IModConfig {
    int maxRenderBlocks = 150;
    int maxShapelessRadius = 6;
    int maxRenderShapelessRadius = 3;
    boolean enabledShapelessVerticalMode = true;
    int maxShapelessVerticalRadius = 6;
    int maxRenderShapelessVerticalRadius = 3;
    boolean enabledTunnelMode = true;
    boolean enabledMineshaftMode = true;
    boolean enabledVegetationMode = true;
    boolean enabledOresMode = true;
    boolean enabledCropsMode = true;
    boolean enabledTreesMode = true;
    boolean enabledFastEat = false;
    boolean enabledFastPlace = false;

    void init();

    int getMaxRenderBlocks();

    void setMaxRenderBlocks(int toSet);

    int getMaxShapelessRadius();

    void setMaxShapelessRadius(int toSet);

    int getMaxRenderShapelessRadius();

    void setMaxRenderShapelessRadius(int toSet);

    boolean isEnabledShapelessVerticalMode();

    void setEnabledShapelessVerticalMode(boolean toSet);

    int getMaxShapelessVerticalRadius();

    void setMaxShapelessVerticalRadius(int toSet);

    int getMaxRenderShapelessVerticalRadius();

    void setMaxRenderShapelessVerticalRadius(int toSet);

    boolean isEnabledTunnelMode();

    void setEnabledTunnelMode(boolean toSet);

    boolean isEnabledMineshaftMode();

    void setEnabledMineshaftMode(boolean toSet);

    boolean isEnabledVegetationMode();

    void setEnabledVegetationMode(boolean toSet);

    boolean isEnabledOresMode();

    void setEnabledOresMode(boolean toSet);

    boolean isEnabledCropsMode();

    void setEnabledCropsMode(boolean toSet);

    boolean isEnabledTreesMode();

    void setEnabledTreesMode(boolean toSet);

    boolean isEnabledFastEat();

    void setEnabledFastEat(boolean toSet);

    boolean isEnabledFastPlace();

    void setEnabledFastPlace(boolean toSet);
}
