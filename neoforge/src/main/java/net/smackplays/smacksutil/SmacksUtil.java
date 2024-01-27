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
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
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
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.networking.C2SPacket.*;
import net.smackplays.smacksutil.networking.S2CPacket.S2CBlockBreakPacket;
import net.smackplays.smacksutil.networking.S2CPacket.S2CBlockBreakPacketHandler;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.AbstractBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractEnchantingToolScreen;
import net.smackplays.smacksutil.screens.AbstractLargeBackpackScreen;
import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;
import top.theillusivec4.curios.api.SlotTypeMessage;

import java.util.function.Supplier;

import static net.smackplays.smacksutil.Constants.*;

@SuppressWarnings("unused")
@Mod(MOD_ID)
public class SmacksUtil {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MOD_ID);
    public static final DeferredItem<Item> BACKPACK_ITEM = ITEMS.register(C_BACKPACK_ITEM, BackpackItem::new);
    public static final DeferredItem<Item> LARGE_BACKPACK_ITEM = ITEMS.register(C_LARGE_BACKPACK_ITEM, LargeBackpackItem::new);
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER1_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER1_ITEM, BackpackUpgradeItem::new);
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER2_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER2_ITEM, BackpackUpgradeItem::new);
    public static final DeferredItem<Item> BACKPACK_UPGRADE_TIER3_ITEM = ITEMS.register(C_BACKPACK_UPGRADE_TIER3_ITEM, BackpackUpgradeItem::new);
    public static final DeferredItem<Item> LIGHT_WAND_ITEM = ITEMS.register(C_LIGHT_WAND_ITEM, () -> new LightWandItem());
    public static final DeferredItem<Item> AUTO_LIGHT_WAND_ITEM = ITEMS.register(C_AUTO_LIGHT_WAND_ITEM, AutoLightWandItem::new);
    public static final DeferredItem<Item> MAGNET_ITEM = ITEMS.register(C_MAGNET_ITEM, () -> new MagnetItem());
    public static final DeferredItem<Item> ADVANCED_MAGNET_ITEM = ITEMS.register(C_ADVANCED_MAGNET_ITEM, AdvancedMagnetItem::new);
    public static final DeferredItem<Item> MOB_CATCHER_ITEM = ITEMS.register(C_MOB_CATCHER_ITEM, MobCatcherItem::new);
    public static final DeferredItem<Item> ADVANCED_MOB_CATCHER_ITEM = ITEMS.register(C_ADVANCED_MOB_CATCHER_ITEM, AdvancedMobCatcherItem::new);
    public static final DeferredItem<Item> ENCHANTING_TOOL_ITEM = ITEMS.register(C_ENCHANTING_TOOL_ITEM, ForgeEnchantingToolItem::new);
    public static final DeferredItem<Item> TELEPORTATION_TABLET_ITEM = ITEMS.register(C_TELEPORTATION_TABLET_ITEM, TeleportationTablet::new);
    public static final DeferredHolder<MenuType<?>, MenuType<BackpackMenu>> BACKPACK_MENU =
            MENUS.register(C_BACKPACK_MENU, () -> new MenuType<>(BackpackMenu::createGeneric9x6, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<LargeBackpackMenu>> LARGE_BACKPACK_MENU =
            MENUS.register(C_LARGE_BACKPACK_MENU, () -> new MenuType<>(LargeBackpackMenu::createGeneric13x9, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<EnchantingToolMenu>> ENCHANTING_TOOL_MENU =
            MENUS.register(C_ENCHANTING_TOOL_MENU, () -> new MenuType<>(EnchantingToolMenu::create, FeatureFlags.DEFAULT_FLAGS));
    public static final DeferredHolder<MenuType<?>, MenuType<TeleportationTabletMenu>> TELEPORTATION_TABLET_MENU =
            MENUS.register(C_TELEPORTATION_TABLET_MENU, () -> new MenuType<>(TeleportationTabletMenu::create, FeatureFlags.DEFAULT_FLAGS));

    public SmacksUtil(IEventBus modEventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        modEventBus.addListener(this::interModEnqueue);
        modEventBus.addListener(this::commonSetup);

        ITEMS.register(modEventBus);
        MENUS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Supplier<Runnable> toRun = () -> ClothConfigNeoForge::registerModsPage;
            toRun.get().run();
        }
        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigNeoForge::registerModsPage);
    }

    public void interModEnqueue(InterModEnqueueEvent e){
        if (Services.PLATFORM.isModLoaded("curios")){
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("charm").size(1).build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("back").size(1).build());
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("hands").size(1).build());
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //event.enqueueWork(PacketHandler::register);
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
            event.accept(TELEPORTATION_TABLET_ITEM);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(SmacksUtil.BACKPACK_MENU.get(), AbstractBackpackScreen<BackpackMenu>::new);
            MenuScreens.register(SmacksUtil.LARGE_BACKPACK_MENU.get(), AbstractLargeBackpackScreen<LargeBackpackMenu>::new);
            MenuScreens.register(SmacksUtil.ENCHANTING_TOOL_MENU.get(), AbstractEnchantingToolScreen<EnchantingToolMenu>::new);
            MenuScreens.register(SmacksUtil.TELEPORTATION_TABLET_MENU.get(), AbstractTeleportationTabletScreen<TeleportationTabletMenu>::new);
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

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class PacketEvents {
        @SubscribeEvent
        public static void register(final RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar sortRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            sortRegistrar.play(C2SSortPacket.ID, C2SSortPacket::new, handler -> handler
                    .server(C2SSortPacketHandler.getInstance()::handleData)
                    .client(C2SSortPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar enchantRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            enchantRegistrar.play(C2SEnchantPacket.ID, C2SEnchantPacket::new, handler -> handler
                    .server(C2SEnchantPacketHandler.getInstance()::handleData)
                    .client(C2SEnchantPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar breakBlockRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            breakBlockRegistrar.play(C2SVeinMinerBreakPacket.ID, C2SVeinMinerBreakPacket::new, handler -> handler
                    .server(C2SVeinMinerBreakPacketHandler.getInstance()::handleData)
                    .client(C2SVeinMinerBreakPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar setBlockAirRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            setBlockAirRegistrar.play(C2SSetBlockAirPacket.ID, C2SSetBlockAirPacket::new, handler -> handler
                    .server(C2SSetBlockAirPacketHandler.getInstance()::handleData)
                    .client(C2SSetBlockAirPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar interactEntityRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            interactEntityRegistrar.play(C2SInteractEntityPacket.ID, C2SInteractEntityPacket::new, handler -> handler
                    .server(C2SInteractEntityPacketHandler.getInstance()::handleData)
                    .client(C2SInteractEntityPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar teleportRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            teleportRegistrar.play(C2STeleportationPacket.ID, C2STeleportationPacket::new, handler -> handler
                    .server(C2STeleportationPacketHandler.getInstance()::handleData)
                    .client(C2STeleportationPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar teleportNBTRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            teleportNBTRegistrar.play(C2STeleportationNBTPacket.ID, C2STeleportationNBTPacket::new, handler -> handler
                    .server(C2STeleportationNBTPacketHandler.getInstance()::handleData)
                    .client(C2STeleportationNBTPacketHandler.getInstance()::handleData));

            final IPayloadRegistrar blockBreakRegistrar = event.registrar(Constants.MOD_ID)
                    .versioned(NeoForgeVersion.getSpec())
                    .optional();
            blockBreakRegistrar.play(S2CBlockBreakPacket.ID, S2CBlockBreakPacket::new, handler -> handler
                    .client(S2CBlockBreakPacketHandler.getInstance()::handleData)
                    .server(S2CBlockBreakPacketHandler.getInstance()::handleData));
        }
    }

}