package net.smackplays.smacksutil.platform;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Attribute getBackpackUpgradeMultiplierAttribute() {
        return SmacksUtil.BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE.get();
    }
}