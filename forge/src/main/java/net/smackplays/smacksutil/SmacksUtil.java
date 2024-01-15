package net.smackplays.smacksutil;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smackplays.smacksutil.config.ClothConfigForge;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.networking.PacketHandler;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;
import org.slf4j.Logger;


@Mod(Constants.MOD_ID)
public class SmacksUtil {
    public static final String MOD_ID = Constants.MOD_ID;
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<MenuType<?>> SCREENS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final RegistryObject<MenuType<BackpackMenu>> GENERIC_9X6 =
            SCREENS.register("backpack_screen", () -> IForgeMenuType.create(BackpackMenu::createGeneric9x6));
    public static final RegistryObject<MenuType<LargeBackpackMenu>> GENERIC_13X9 =
            SCREENS.register("large_backpack_screen", () -> IForgeMenuType.create(LargeBackpackMenu::createGeneric13x9));
    public static final RegistryObject<MenuType<EnchantingToolMenu>> ENCHANTING_TOOL =
            SCREENS.register("enchanting_tool", () -> IForgeMenuType.create(EnchantingToolMenu::create));
    public static final RegistryObject<Item> BACKPACK_ITEM = ITEMS.register("backpack_item", BackpackItem::new);
    public static final RegistryObject<Item> LARGE_BACKPACK_ITEM = ITEMS.register("large_backpack_item", LargeBackpackItem::new);
    public static final RegistryObject<Item> LIGHT_WAND = ITEMS.register("light_wand", () ->
            new LightWand(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final RegistryObject<Item> AUTO_LIGHT_WAND = ITEMS.register("auto_light_wand", () ->
            new AutoLightWand(new Item.Properties().rarity(Rarity.EPIC).durability(2000)));
    public static final RegistryObject<Item> MAGNET_ITEM = ITEMS.register("magnet_item", () ->
            new MagnetItem(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final RegistryObject<Item> ADVANCED_MAGNET_ITEM = ITEMS.register("advanced_magnet_item", () ->
            new AdvancedMagnetItem(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final RegistryObject<Item> MOB_TOOL_ITEM = ITEMS.register("mob_imprisonment_tool", () ->
            new MobImprisonmentTool(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final RegistryObject<Item> ADV_MOB_TOOL_ITEM = ITEMS.register("advanced_mob_imp_tool", () ->
            new AdvancedMobImpTool(new Item.Properties().rarity(Rarity.EPIC).durability(2000)));
    public static final RegistryObject<Item> ENCH_TOOL = ITEMS.register("enchanting_tool", () ->
            new ForgeEnchantingTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    private static final Logger LOGGER = LogUtils.getLogger();
    public SmacksUtil() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        SCREENS.register(modEventBus);

        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigForge::registerModsPage);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_ITEM);
            event.accept(LARGE_BACKPACK_ITEM);
            event.accept(LIGHT_WAND);
            event.accept(AUTO_LIGHT_WAND);
            event.accept(MAGNET_ITEM);
            event.accept(ADVANCED_MAGNET_ITEM);
            event.accept(MOB_TOOL_ITEM);
            event.accept(ADV_MOB_TOOL_ITEM);
            event.accept(ENCH_TOOL);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(SmacksUtil.GENERIC_13X9.get(), LargeBackpackScreen::new);
            MenuScreens.register(SmacksUtil.GENERIC_9X6.get(), BackpackScreen::new);
            MenuScreens.register(SmacksUtil.ENCHANTING_TOOL.get(), EnchantingToolScreen::new);
            CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM.get(), CauldronInteraction.DYED_ITEM);
            CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM.get(), CauldronInteraction.DYED_ITEM);
        }

        @SubscribeEvent
        public static void colors(RegisterColorHandlersEvent.Item event) {
            event.register((ItemStack stack, int tintIndex) -> tintIndex == 0 ?
                    ((DyeableLeatherItem) BACKPACK_ITEM.get()).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM.get());
            event.register((ItemStack stack, int tintIndex) -> tintIndex == 0 ?
                    ((DyeableLeatherItem) LARGE_BACKPACK_ITEM.get()).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM.get());
        }
    }






}