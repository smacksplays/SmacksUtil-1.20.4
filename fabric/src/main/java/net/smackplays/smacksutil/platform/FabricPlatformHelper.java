package net.smackplays.smacksutil.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.smackplays.smacksutil.ModClient;
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
        return ModClient.BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE;
    }
}
