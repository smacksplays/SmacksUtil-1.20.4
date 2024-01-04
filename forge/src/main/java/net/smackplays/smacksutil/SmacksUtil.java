package net.smackplays.smacksutil;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
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
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackContainerMenu;
import net.smackplays.smacksutil.backpacks.LargeBackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreen;
import net.smackplays.smacksutil.config.ClothConfigForge;
import net.smackplays.smacksutil.veinminer.Miner;
import org.slf4j.Logger;


@Mod(Constants.MOD_ID)
public class SmacksUtil {
    public static final String MOD_ID = Constants.MOD_ID;
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> BACKPACK_ITEM = ITEMS.register("backpack_item", BackpackItem::new);
    public static final RegistryObject<Item> LARGE_BACKPACK_ITEM = ITEMS.register("large_backpack_item", LargeBackpackItem::new);
    public static final DeferredRegister<MenuType<?>> SCREEN = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final RegistryObject<MenuType<LargeBackpackContainerMenu>> GENERIC_13X9 =
            SCREEN.register("large_backpack_screen", () -> IForgeMenuType.create(LargeBackpackContainerMenu::createGeneric13x9));
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Miner veinMiner;

    public SmacksUtil() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
        veinMiner = new Miner();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        SCREEN.register(modEventBus);

        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClothConfigForge::registerModsPage);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(SmacksUtil.GENERIC_13X9.get(), LargeBackpackScreen::new);
            CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM.get(), CauldronInteraction.SHULKER_BOX);
            CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM.get(), CauldronInteraction.SHULKER_BOX);
        }
    }
}