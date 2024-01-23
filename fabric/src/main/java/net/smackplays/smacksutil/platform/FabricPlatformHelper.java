package net.smackplays.smacksutil.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Attribute getBackpackUpgradeMultiplierAttribute() {
        return SmacksUtil.BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE;
    }

    @Override
    public Item getLightWandItem() {
        return SmacksUtil.LIGHT_WAND_ITEM;
    }

    @Override
    public Item getAutoWandItem() {
        return SmacksUtil.AUTO_LIGHT_WAND_ITEM;
    }

    @Override
    public boolean isClient(){
        EnvType t = FabricLoader.getInstance().getEnvironmentType();
        return t.name().equals("CLIENT");
    }
}
