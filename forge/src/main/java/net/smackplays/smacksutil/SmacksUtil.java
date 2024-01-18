package net.smackplays.smacksutil;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

import static net.smackplays.smacksutil.Constants.*;


@SuppressWarnings("unused")
@Mod(MOD_ID)
public class SmacksUtil {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<MenuType<?>> SCREENS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);
    public static final RegistryObject<Attribute> BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE
            = ATTRIBUTES.register("generic.upgrade_multiplier", ()
            -> new RangedAttribute("attribute.name.generic.upgrade_multiplier", 1, 1, 8));

    public static final RegistryObject<Item> BACKPACK_ITEM = ITEMS.register(C_BACKPACK_ITEM, BackpackItem::new);
    public static final RegistryObject<Item> LARGE_BACKPACK_ITEM = ITEMS.register(C_LARGE_BACKPACK_ITEM, LargeBackpackItem::new);
    public static final RegistryObject<Item> BACKPACK_UPGRADE_TIER1_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER1_ITEM, () -> new BackpackUpgradeItem(2));
    public static final RegistryObject<Item> BACKPACK_UPGRADE_TIER2_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER2_ITEM, () -> new BackpackUpgradeItem(4));
    public static final RegistryObject<Item> BACKPACK_UPGRADE_TIER3_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER3_ITEM, () -> new BackpackUpgradeItem(8));
    public static final RegistryObject<Item> LIGHT_WAND_ITEM = ITEMS.register(C_LIGHT_WAND_ITEM, LightWandItem::new);
    public static final RegistryObject<Item> AUTO_LIGHT_WAND_ITEM = ITEMS.register(C_AUTO_LIGHT_WAND_ITEM, AutoLightWandItem::new);
    public static final RegistryObject<Item> MAGNET_ITEM = ITEMS.register(C_MAGNET_ITEM, MagnetItem::new);
    public static final RegistryObject<Item> ADVANCED_MAGNET_ITEM = ITEMS.register(C_ADVANCED_MAGNET_ITEM, AdvancedMagnetItem::new);
    public static final RegistryObject<Item> MOB_CATCHER_ITEM = ITEMS.register(C_MOB_CATCHER_ITEM, MobCatcherItem::new);
    public static final RegistryObject<Item> ADVANCED_MOB_CATCHER_ITEM = ITEMS.register(C_ADVANCED_MOB_CATCHER_ITEM, AdvancedMobCatcherItem::new);
    public static final RegistryObject<Item> ENCHANTING_TOOL_ITEM = ITEMS.register(C_ENCHANTING_TOOL_ITEM, ForgeEnchantingToolItem::new);
    public static final RegistryObject<MenuType<EnchantingToolMenu>> ENCHANTING_TOOL_SCREEN =
            SCREENS.register(C_ENCHANTING_TOOL_SCREEN, () -> IForgeMenuType.create(EnchantingToolMenu::create));
    public static final RegistryObject<MenuType<BackpackMenu>> BACKPACK_SCREEN =
            SCREENS.register(C_BACKPACK_SCREEN, () -> IForgeMenuType.create(BackpackMenu::createGeneric9x6));
    public static final RegistryObject<MenuType<LargeBackpackMenu>> LARGE_BACKPACK_SCREEN =
            SCREENS.register(C_LARGE_BACKPACK_SCREEN, () -> IForgeMenuType.create(LargeBackpackMenu::createGeneric13x9));

    public SmacksUtil() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ATTRIBUTES.register(modEventBus);

        SCREENS.register(modEventBus);

        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigForge::registerModsPage);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PacketHandler::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(BACKPACK_ITEM);
            event.accept(LARGE_BACKPACK_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER1_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER2_ITEM);
            event.accept(BACKPACK_UPGRADE_TIER3_ITEM);
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
    }


}