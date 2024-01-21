package net.smackplays.smacksutil;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.smackplays.smacksutil.events.veinminer.PlayerBlockBreak;
import net.smackplays.smacksutil.items.*;
import net.smackplays.smacksutil.menus.BackpackMenu;
import net.smackplays.smacksutil.menus.EnchantingToolMenu;
import net.smackplays.smacksutil.menus.LargeBackpackMenu;
import net.smackplays.smacksutil.menus.TeleportationTabletMenu;
import net.smackplays.smacksutil.screens.BackpackScreen;
import net.smackplays.smacksutil.screens.EnchantingToolScreen;
import net.smackplays.smacksutil.screens.LargeBackpackScreen;
import net.smackplays.smacksutil.screens.TeleportationTabletScreen;

import java.util.Set;

import static net.smackplays.smacksutil.Constants.*;

public class SmacksUtil implements ModInitializer {

    public static final Attribute BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE = new RangedAttribute("attribute.name.generic.upgrade_multiplier", 1, 1, 8);
    public static final Item BACKPACK_ITEM = new BackpackItem();
    public static final Item LARGE_BACKPACK_ITEM = new LargeBackpackItem();
    public static final Item BACKPACK_UPGRADE_TIER1_ITEM = new BackpackUpgradeItem(2);
    public static final Item BACKPACK_UPGRADE_TIER2_ITEM = new BackpackUpgradeItem(4);
    public static final Item BACKPACK_UPGRADE_TIER3_ITEM = new BackpackUpgradeItem(8);
    public static final Item LIGHT_WAND_ITEM = new LightWandItem();
    public static final Item AUTO_LIGHT_WAND_ITEM = new AutoLightWandItem();
    public static final Item MAGNET_ITEM = new MagnetItem();
    public static final Item ADVANCED_MAGNET_ITEM = new AdvancedMagnetItem();
    public static final Item MOB_CATCHER_ITEM = new MobCatcherItem();
    public static final Item ADVANCED_MOB_CATCHER_ITEM = new AdvancedMobCatcherItem();
    public static final Item ENCHANTING_TOOL_ITEM = new FabricEnchantingToolItem();
    public static final Item TELEPORTATION_TABLET_ITEM = new TeleportationTablet();
    public static final ResourceLocation ENCHANT_REQUEST_ID = new ResourceLocation(MOD_ID, "enchant-request");
    public static final ResourceLocation SORT_REQUEST_ID = new ResourceLocation(MOD_ID, "sort-request");
    public static final ResourceLocation BREAK_BLOCK_REQUEST_ID = new ResourceLocation(MOD_ID, "break-block-request");
    public static final ResourceLocation TELEPORT_REQUEST_ID = new ResourceLocation(MOD_ID, "teleport-request");
    public static final ResourceLocation TELEPORT_NBT_REQUEST_ID = new ResourceLocation(MOD_ID, "teleport-nbt-request");
    public static final MenuType<BackpackMenu> BACKPACK_MENU = new ExtendedScreenHandlerType<>(BackpackMenu::createGeneric9x6);
    public static final MenuType<LargeBackpackMenu> LARGE_BACKPACK_MENU = new ExtendedScreenHandlerType<>(LargeBackpackMenu::createGeneric13x9);
    public static final MenuType<EnchantingToolMenu> ENCHANTING_TOOL_MENU = new ExtendedScreenHandlerType<>(EnchantingToolMenu::create);
    public static final MenuType<TeleportationTabletMenu> TELEPORTATION_TABLET_MENU = new ExtendedScreenHandlerType<>(TeleportationTabletMenu::create);

    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        PlayerBlockBreakEvents.BEFORE.register(new PlayerBlockBreak());

        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_LARGE_BACKPACK_MENU), LARGE_BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_BACKPACK_MENU), BACKPACK_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_ENCHANTING_TOOL_MENU), ENCHANTING_TOOL_MENU);
        Registry.register(BuiltInRegistries.MENU, new ResourceLocation(MOD_ID, C_TELEPORTATION_TABLET_MENU), TELEPORTATION_TABLET_MENU);


        registerItem(C_BACKPACK_ITEM, BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);

        registerItem(C_LARGE_BACKPACK_ITEM, LARGE_BACKPACK_ITEM);
        CauldronInteraction.WATER.map().putIfAbsent(LARGE_BACKPACK_ITEM, CauldronInteraction.SHULKER_BOX);

        Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(MOD_ID, "generic.upgrade_multiplier"), BACKPACK_UPGRADE_MULTIPLIER_ATTRIBUTE);
        registerItem(C_BACKPACK_UPGRADE_TIER1_ITEM, BACKPACK_UPGRADE_TIER1_ITEM);
        registerItem(C_BACKPACK_UPGRADE_TIER2_ITEM, BACKPACK_UPGRADE_TIER2_ITEM);
        registerItem(C_BACKPACK_UPGRADE_TIER3_ITEM, BACKPACK_UPGRADE_TIER3_ITEM);

        registerItem(C_ENCHANTING_TOOL_ITEM, ENCHANTING_TOOL_ITEM);

        registerItem(C_LIGHT_WAND_ITEM, LIGHT_WAND_ITEM);
        registerItem(C_AUTO_LIGHT_WAND_ITEM, AUTO_LIGHT_WAND_ITEM);
        registerItem(C_MAGNET_ITEM, MAGNET_ITEM);
        registerItem(C_ADVANCED_MAGNET_ITEM, ADVANCED_MAGNET_ITEM);
        registerItem(C_MOB_CATCHER_ITEM, MOB_CATCHER_ITEM);
        registerItem(C_ADVANCED_MOB_CATCHER_ITEM, ADVANCED_MOB_CATCHER_ITEM);
        registerItem(C_TELEPORTATION_TABLET_ITEM, TELEPORTATION_TABLET_ITEM);

        ServerPlayNetworking.registerGlobalReceiver(ENCHANT_REQUEST_ID, this::handleEnchantRequest);

        ServerPlayNetworking.registerGlobalReceiver(SORT_REQUEST_ID, this::handleSortRequest);

        ServerPlayNetworking.registerGlobalReceiver(BREAK_BLOCK_REQUEST_ID, this::handleBreakBlockRequest);

        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_NBT_REQUEST_ID, this::handleTeleportNBTRequest);

        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_REQUEST_ID, this::handleTeleportRequest);
    }

    private void registerItem(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(itemGroup -> itemGroup.accept(item));
    }

    private void handleEnchantRequest(MinecraftServer server, ServerPlayer player,
                                      ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            ItemStack item = buf.readItem();
            screenHandler.slots.get(0).set(item);
        });
    }

    private void handleSortRequest(MinecraftServer server, ServerPlayer player,
                                   ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            AbstractContainerMenu screenHandler = player.containerMenu;
            ItemStack stack = buf.readItem();
            if (stack.getItem() instanceof LargeBackpackItem && screenHandler instanceof LargeBackpackMenu lBackpackMenu) {
                lBackpackMenu.sort();
            } else if (stack.getItem() instanceof AbstractBackpackItem && screenHandler instanceof BackpackMenu backpackMenu) {
                backpackMenu.sort();
            }
        });
    }

    private void handleBreakBlockRequest(MinecraftServer server, ServerPlayer player,
                                         ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            Level world = player.level();
            BlockPos pos = buf.readBlockPos();
            if (world.isClientSide) return;
            world.destroyBlock(pos, true);
        });
    }

    private void handleTeleportNBTRequest(MinecraftServer server, ServerPlayer player,
                                          ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            ItemStack stack = buf.readItem();
            Vec3 pos = buf.readVec3();
            float xRot = buf.readFloat();
            float yRot = buf.readFloat();
            String name = buf.readUtf();
            String dim = buf.readUtf();
            boolean remove = buf.readBoolean();


            CompoundTag tag = stack.getOrCreateTag();
            ListTag posTag = (ListTag)tag.get("Positions");
            if (posTag == null) {
                posTag = new ListTag();
            }
            CompoundTag teleportTag = new CompoundTag();
            teleportTag.putString("name", name);
            teleportTag.putDouble("x_pos", pos.x());
            teleportTag.putDouble("y_pos", pos.y());
            teleportTag.putDouble("z_pos", pos.z());
            teleportTag.putDouble("x_rot", xRot);
            teleportTag.putDouble("y_rot", yRot);
            teleportTag.putString("dim", dim);
            if (remove){
                for (int i = 0; i < posTag.size(); i++){
                    CompoundTag t = (CompoundTag) posTag.get(i);
                    if (t.contains("name")){
                        String n = t.getString("name");
                        if (n.equals(name)){
                            posTag.remove(i);
                            tag.put("Positions", posTag);
                            stack.setTag(tag);
                            player.getInventory().setItem(player.getInventory().selected, stack);
                            player.inventoryMenu.broadcastChanges();
                            return;
                        }
                    }
                }
            } else {
                posTag.add(teleportTag);
            }
            tag.put("Positions", posTag);
            stack.setTag(tag);
            player.getInventory().setItem(player.getInventory().selected, stack);
            player.inventoryMenu.broadcastChanges();
        });
    }

    private void handleTeleportRequest(MinecraftServer server, ServerPlayer player,
                                       ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender){
        server.execute(() -> {
            ResourceKey<?> tempKey = buf.readRegistryKey();
            ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, tempKey.location());
            Vec3 pos = buf.readVec3();
            float xRot = buf.readFloat();
            float yRot = buf.readFloat();

            ServerLevel serverLevel = server.getLevel(levelKey);
            //player.teleportTo(pos.x, pos.y, pos.z);
            if (serverLevel != null) {
                player.teleportTo(serverLevel, pos.x, pos.y, pos.z, Set.of(), yRot, xRot);
            }
        });
    }


}
