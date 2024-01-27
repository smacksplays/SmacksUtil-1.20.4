package net.smackplays.smacksutil.platform;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
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
    public Item getLightWandItem() {
        if (SmacksUtil.LIGHT_WAND_ITEM.isBound()) {
            return SmacksUtil.LIGHT_WAND_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getAutoWandItem() {
        if (SmacksUtil.AUTO_LIGHT_WAND_ITEM.isBound()) {
            return SmacksUtil.AUTO_LIGHT_WAND_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade1Item() {
        if (SmacksUtil.BACKPACK_UPGRADE_TIER1_ITEM.isBound()) {
            return SmacksUtil.BACKPACK_UPGRADE_TIER1_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade2Item() {
        if (SmacksUtil.BACKPACK_UPGRADE_TIER2_ITEM.isBound()) {
            return SmacksUtil.BACKPACK_UPGRADE_TIER2_ITEM.get();
        }
        return null;
    }

    @Override
    public Item getUpgrade3Item() {
        if (SmacksUtil.BACKPACK_UPGRADE_TIER3_ITEM.isBound()) {
            return SmacksUtil.BACKPACK_UPGRADE_TIER3_ITEM.get();
        }
        return null;
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getDist().isClient();
    }
}