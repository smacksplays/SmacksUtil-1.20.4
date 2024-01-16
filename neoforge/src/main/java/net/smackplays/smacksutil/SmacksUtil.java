package net.smackplays.smacksutil;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.DistExecutor;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.internal.versions.neoforge.NeoForgeVersion;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.smackplays.smacksutil.config.ClothConfigNeoForge;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.networking.EnchantData;
import net.smackplays.smacksutil.networking.ServerEnchantPayloadHandler;
import net.smackplays.smacksutil.networking.ServerSortPayloadHandler;
import net.smackplays.smacksutil.networking.SortData;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;

import static net.smackplays.smacksutil.Constants.MOD_ID;

@Mod(MOD_ID)
public class SmacksUtil {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> SCREENS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<MenuType<?>, MenuType<EnchantingToolMenu>> ENCHANTING_TOOL =
            SCREENS.register("enchanting_tool", () -> new MenuType<>(EnchantingToolMenu::create, FeatureFlags.DEFAULT_FLAGS));    public static final DeferredHolder<MenuType<?>, MenuType<BackpackMenu>> GENERIC_9X6 =
            SCREENS.register("backpack_screen", () -> new MenuType<>(BackpackMenu::createGeneric9x6, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredItem<Item> BACKPACK_ITEM = ITEMS.register("backpack_item", BackpackItem::new);    public static final DeferredHolder<MenuType<?>, MenuType<LargeBackpackMenu>> GENERIC_13X9 =
            SCREENS.register("large_backpack_screen", () -> new MenuType<>(LargeBackpackMenu::createGeneric13x9, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredItem<Item> LARGE_BACKPACK_ITEM = ITEMS.register("large_backpack_item", LargeBackpackItem::new);
    public static final DeferredItem<Item> LIGHT_WAND = ITEMS.register("light_wand", () ->
            new LightWand(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final DeferredItem<Item> AUTO_LIGHT_WAND = ITEMS.register("auto_light_wand", () ->
            new AutoLightWand(new Item.Properties().rarity(Rarity.EPIC).durability(2000)));
    public static final DeferredItem<Item> MAGNET_ITEM = ITEMS.register("magnet_item", () ->
            new MagnetItem(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final DeferredItem<Item> ADVANCED_MAGNET_ITEM = ITEMS.register("advanced_magnet_item", () ->
            new AdvancedMagnetItem(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final DeferredItem<Item> MOB_TOOL_ITEM = ITEMS.register("mob_imprisonment_tool", () ->
            new MobImprisonmentTool(new Item.Properties().rarity(Rarity.EPIC).durability(200)));
    public static final DeferredItem<Item> ADV_MOB_TOOL_ITEM = ITEMS.register("advanced_mob_imp_tool", () ->
            new AdvancedMobImpTool(new Item.Properties().rarity(Rarity.EPIC).durability(2000)));
    public static final DeferredItem<Item> ENCH_TOOL = ITEMS.register("enchanting_tool", () ->
            new ForgeEnchantingTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public SmacksUtil(IEventBus modEventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        SCREENS.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigNeoForge::registerModsPage);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        //event.enqueueWork(PacketHandler::register);
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

        @SubscribeEvent
        public static void register(final RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar sortRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            sortRegistrar.play(SortData.ID, SortData::new, handler -> handler
                    .server(ServerSortPayloadHandler.getInstance()::handleData));
            final IPayloadRegistrar enchantRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            enchantRegistrar.play(EnchantData.ID, EnchantData::new, handler -> handler
                    .server(ServerEnchantPayloadHandler.getInstance()::handleData));

        }
    }

}