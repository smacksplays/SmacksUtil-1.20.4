package net.smackplays.smacksutil.trinkets;

import dev.emi.trinkets.api.TrinketsApi;
import net.smackplays.smacksutil.SmacksUtil;

public class Trinkets {
    public static void init()
    {
        TrinketsApi.registerTrinket(SmacksUtil.BACKPACK_ITEM, new SmacksTrinket());
        TrinketsApi.registerTrinket(SmacksUtil.LARGE_BACKPACK_ITEM, new SmacksTrinket());
        TrinketsApi.registerTrinket(SmacksUtil.MAGNET_ITEM, new SmacksTrinket());
        TrinketsApi.registerTrinket(SmacksUtil.ADVANCED_MAGNET_ITEM, new SmacksTrinket());
        TrinketsApi.registerTrinket(SmacksUtil.LIGHT_WAND_ITEM, new SmacksTrinket());
        TrinketsApi.registerTrinket(SmacksUtil.AUTO_LIGHT_WAND_ITEM, new SmacksTrinket());
    }
}
