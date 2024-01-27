package net.smackplays.smacksutil.platform;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.platform.services.IPlatformHelper;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
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
        return SmacksUtil.LIGHT_WAND_ITEM.get();
    }

    @Override
    public Item getAutoWandItem() {
        return SmacksUtil.AUTO_LIGHT_WAND_ITEM.get();
    }

    @Override
    public Item getUpgrade1Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER1_ITEM.get();
    }

    @Override
    public Item getUpgrade2Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER2_ITEM.get();
    }

    @Override
    public Item getUpgrade3Item() {
        return SmacksUtil.BACKPACK_UPGRADE_TIER3_ITEM.get();
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getDist().isClient();
    }
}