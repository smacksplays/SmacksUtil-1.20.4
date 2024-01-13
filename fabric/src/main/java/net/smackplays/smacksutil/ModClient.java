package net.smackplays.smacksutil;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.smackplays.smacksutil.backpacks.BackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackItem;
import net.smackplays.smacksutil.backpacks.LargeBackpackScreen;
import net.smackplays.smacksutil.blockEntities.FabricControllerBlockEntity;
import net.smackplays.smacksutil.blockEntities.FabricDiskReaderBlockEntity;
import net.smackplays.smacksutil.blockEntities.FabricItemMonitorBlockEntity;
import net.smackplays.smacksutil.blocks.FabricControllerBlock;
import net.smackplays.smacksutil.blocks.FabricDiskReaderBlock;
import net.smackplays.smacksutil.blocks.FabricItemMonitorBlock;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.screens.FabricDiskReaderScreen;
import net.smackplays.smacksutil.screens.FabricItemMonitorScreen;

public class ModClient implements ClientModInitializer {

    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    public static final Item LIGHT_WAND = new LightWand(new Item.Properties().rarity(Rarity.EPIC).durability(200));
    public static final Item AUTO_LIGHT_WAND = new AutoLightWand(new Item.Properties().rarity(Rarity.EPIC).durability(2000));
    public static final Item MAGNET_ITEM = new MagnetItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item ADVANCED_MAGNET_ITEM = new AdvancedMagnetItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item MOB_TOOL_ITEM = new MobImprisonmentTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Item ADV_MOB_TOOL_ITEM = new AdvancedMobImpTool(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    public static final Block DISK_READER_BLOCK = new FabricDiskReaderBlock(FabricBlockSettings.create().strength(4.0F));
    public static final Item DISK_READER_ITEM = new BlockItem(DISK_READER_BLOCK, new Item.Properties());
    public static final Item DISK_ITEM = new DiskItem(new Item.Properties().stacksTo(1));    public static final BlockEntityType<FabricDiskReaderBlockEntity> DISK_READER_ENTITY_TYPE =
            FabricBlockEntityTypeBuilder.create(FabricDiskReaderBlockEntity::new, DISK_READER_BLOCK).build();
    public static final Block ITEM_MONITOR_BLOCK = new FabricItemMonitorBlock(FabricBlockSettings.create().strength(4.0F));
    public static final Item ITEM_MONITOR_ITEM = new BlockItem(ITEM_MONITOR_BLOCK, new Item.Properties());
    public static final Block CONTROLLER_BLOCK = new FabricControllerBlock(FabricBlockSettings.create().strength(4.0F));
    public static final Item CONTROLLER_ITEM = new BlockItem(CONTROLLER_BLOCK, new Item.Properties());    public static final BlockEntityType<FabricItemMonitorBlockEntity> ITEM_MONITOR_ENTITY_TYPE =
            FabricBlockEntityTypeBuilder.create(FabricItemMonitorBlockEntity::new, ITEM_MONITOR_BLOCK).build();

    @Override
    public void onInitializeClient() {
        Services.KEY_HANDLER.register();

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "backpack_item"), BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(BACKPACK_ITEM));

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "large_backpack_item"), LARGE_BACKPACK_ITEM);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? ((DyeableLeatherItem) LARGE_BACKPACK_ITEM).getColor(stack) : 0xFFFFFF, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(LARGE_BACKPACK_ITEM));
        MenuScreens.register(SmacksUtil.GENERIC_13X9, LargeBackpackScreen::new);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "light_wand"), LIGHT_WAND);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "auto_light_wand"), AUTO_LIGHT_WAND);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "magnet_item"), MAGNET_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "advanced_magnet_item"), ADVANCED_MAGNET_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "mob_imprisonment_tool"), MOB_TOOL_ITEM);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "advanced_mob_imp_tool"), ADV_MOB_TOOL_ITEM);

        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "disk_item"), DISK_ITEM);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Constants.MOD_ID, "disk_reader_block"), DISK_READER_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "disk_reader"), DISK_READER_ITEM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, "disk_reader_entity"), DISK_READER_ENTITY_TYPE);
        MenuScreens.register(SmacksUtil.DISK_READER_MENU, FabricDiskReaderScreen::new);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Constants.MOD_ID, "item_monitor_block"), ITEM_MONITOR_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "item_monitor"), ITEM_MONITOR_ITEM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, "item_monitor_entity"), ITEM_MONITOR_ENTITY_TYPE);
        MenuScreens.register(SmacksUtil.ITEM_MONITOR_MENU, FabricItemMonitorScreen::new);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Constants.MOD_ID, "controller_block"), CONTROLLER_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Constants.MOD_ID, "controller"), CONTROLLER_ITEM);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Constants.MOD_ID, "controller_entity"), CONTROLLER_ENTITY_TYPE);

    }

    public static final BlockEntityType<FabricControllerBlockEntity> CONTROLLER_ENTITY_TYPE =
            FabricBlockEntityTypeBuilder.create(FabricControllerBlockEntity::new, CONTROLLER_BLOCK).build();


}
