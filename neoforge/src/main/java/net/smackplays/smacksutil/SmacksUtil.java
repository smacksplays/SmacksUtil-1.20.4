package net.smackplays.smacksutil;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
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
import net.smackplays.smacksutil.networking.*;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;

import java.util.function.Supplier;

import static net.smackplays.smacksutil.Constants.*;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class SmacksUtil {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> SCREENS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredItem<Item> BACKPACK_ITEM = ITEMS.register(C_BACKPACK_ITEM, BackpackItem::new);
    public static final DeferredItem<Item> LARGE_BACKPACK_ITEM = ITEMS.register(C_LARGE_BACKPACK_ITEM, LargeBackpackItem::new);
    public static final DeferredItem<Item> LIGHT_WAND_ITEM = ITEMS.register(C_LIGHT_WAND_ITEM, () -> new LightWandItem());
    public static final DeferredItem<Item> AUTO_LIGHT_WAND_ITEM = ITEMS.register(C_AUTO_LIGHT_WAND_ITEM, AutoLightWandItem::new);
    public static final DeferredItem<Item> MAGNET_ITEM = ITEMS.register(C_MAGNET_ITEM, () -> new MagnetItem());
    public static final DeferredItem<Item> ADVANCED_MAGNET_ITEM = ITEMS.register(C_ADVANCED_MAGNET_ITEM, AdvancedMagnetItem::new);
    public static final DeferredItem<Item> MOB_CATCHER_ITEM = ITEMS.register(C_MOB_CATCHER_ITEM, MobCatcherItem::new);
    public static final DeferredItem<Item> ADVANCED_MOB_CATCHER_ITEM = ITEMS.register(C_ADVANCED_MOB_CATCHER_ITEM, AdvancedMobCatcherItem::new);
    public static final DeferredItem<Item> ENCHANTING_TOOL_ITEM = ITEMS.register(C_ENCHANTING_TOOL_ITEM, ForgeEnchantingToolItem::new);
    public static final DeferredHolder<MenuType<?>, MenuType<BackpackMenu>> BACKPACK_SCREEN =
            SCREENS.register(C_BACKPACK_SCREEN, () -> new MenuType<>(BackpackMenu::createGeneric9x6, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<LargeBackpackMenu>> LARGE_BACKPACK_SCREEN =
            SCREENS.register(C_LARGE_BACKPACK_SCREEN, () -> new MenuType<>(LargeBackpackMenu::createGeneric13x9, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EnchantingToolMenu>> ENCHANTING_TOOL_SCREEN =
            SCREENS.register(C_ENCHANTING_TOOL_SCREEN, () -> new MenuType<>(EnchantingToolMenu::create, FeatureFlags.DEFAULT_FLAGS));

    public SmacksUtil(IEventBus modEventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        SCREENS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Supplier<Runnable> toRun = () -> ClothConfigNeoForge::registerModsPage;
            toRun.get().run();
        }
        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigNeoForge::registerModsPage);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //event.enqueueWork(PacketHandler::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_ITEM);
            event.accept(LARGE_BACKPACK_ITEM);
            event.accept(LIGHT_WAND_ITEM);
            event.accept(AUTO_LIGHT_WAND_ITEM);
            event.accept(MAGNET_ITEM);
            event.accept(ADVANCED_MAGNET_ITEM);
            event.accept(MOB_CATCHER_ITEM);
            event.accept(ADVANCED_MOB_CATCHER_ITEM);
            event.accept(ENCHANTING_TOOL_ITEM);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(SmacksUtil.LARGE_BACKPACK_SCREEN.get(), LargeBackpackScreen::new);
            MenuScreens.register(SmacksUtil.BACKPACK_SCREEN.get(), BackpackScreen::new);
            MenuScreens.register(SmacksUtil.ENCHANTING_TOOL_SCREEN.get(), EnchantingToolScreen::new);
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
            final IPayloadRegistrar breakBlockRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            breakBlockRegistrar.play(BreakBlockData.ID, BreakBlockData::new, handler -> handler
                    .server(ServerBreakBlockPayloadHandler.getInstance()::handleData));

        }
    }


}