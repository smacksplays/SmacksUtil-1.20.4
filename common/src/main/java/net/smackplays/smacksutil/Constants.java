package net.smackplays.smacksutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class Constants {

    public static final String MOD_ID = "smacksutil";
    public static final String MOD_NAME = "SmacksUtil";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final int GREEN = 65280;
    public static final int RED = 16711680;
    public static final int WHITE = 16777215;
    public static final int DARK_GRAY = 3487284;
    public static final int C_VEINMINER_UPDATE_RATE = 128;
    // --------------------------------- BACKPACKS
    public static final String C_BACKPACK_ITEM = "backpack_item";
    public static final String C_LARGE_BACKPACK_ITEM = "large_backpack_item";
    public static final String C_BACKPACK_MENU = "backpack_menu";
    public static final String C_LARGE_BACKPACK_MENU = "large_backpack_menu";
    public static final String C_BACKPACK_SCREEN_LOCATION = "textures/gui/container/backpack_screen.png";
    public static final String C_LARGE_BACKPACK_SCREEN_LOCATION = "textures/gui/container/large_backpack_screen.png";
    public static final String C_BACKPACK_SORT_REQUEST = "backpack-sort-request";
    public static final String C_BACKPACK_OPEN_REQUEST = "backpack-open-request";
    // --------------------------------- BACKPACK UPGRADES
    public static final String C_BACKPACK_UPGRADE_TIER1_ITEM = "backpack_upgrade_tier1_item";
    public static final String C_BACKPACK_UPGRADE_TIER2_ITEM = "backpack_upgrade_tier2_item";
    public static final String C_BACKPACK_UPGRADE_TIER3_ITEM = "backpack_upgrade_tier3_item";
    // --------------------------------- LIGHT WANDS
    public static final String C_LIGHT_WAND_ITEM = "light_wand_item";
    public static final String C_AUTO_LIGHT_WAND_ITEM = "auto_light_wand_item";
    public static final String C_TOGGLE_LIGHT_WAND_REQUEST = "toggle-light-wand-request";
    // --------------------------------- MAGNETS
    public static final String C_MAGNET_ITEM = "magnet_item";
    public static final String C_ADVANCED_MAGNET_ITEM = "advanced_magnet_item";
    public static final String C_TOGGLE_MAGNET_ITEM_REQUEST = "toggle-magnet-item-request";
    // --------------------------------- MOB CATCHERS
    public static final String C_MOB_CATCHER_ITEM = "mob_catcher_item";
    public static final String C_ADVANCED_MOB_CATCHER_ITEM = "advanced_mob_catcher_item";
    // --------------------------------- ENCHANTING TOOL
    public static final String C_ENCHANTING_TOOL_ITEM = "enchanting_tool_item";
    public static final String C_ENCHANTING_TOOL_MENU = "enchanting_tool_menu";
    public static final String C_ENCHANTING_TOOL_SCREEN_LOCATION = "textures/gui/container/enchanting_tool_screen.png";
    public static final String C_ENCHANT_REQUEST = "enchant-request";
    // --------------------------------- TELEPORTATION TABLET
    public static final String C_TELEPORTATION_TABLET_ITEM = "teleportation_tablet_item";
    public static final String C_TELEPORTATION_TABLET_MENU = "teleportation_tablet_menu";
    public static final String C_TELEPORTATION_TABLET_SCREEN_LOCATION = "textures/gui/container/teleportation_tablet_screen.png";
    public static final String C_TELEPORT_REQUEST = "teleport-request";
    public static final String C_TELEPORT_NBT_REQUEST = "teleport-nbt-request";
    // --------------------------------- SPRITES
    public static final String C_ENCHANTING_SLOT_HIGHLIGHTED_SPRITE_LOCATION = "textures/gui/sprites/enchanting_slot_highlighted.png";
    public static final String C_ENCHANTING_SLOT_SPRITE_LOCATION = "textures/gui/sprites/enchanting_slot.png";
    public static final String C_ENCHANTING_SLOT_DISABLED_SPRITE_LOCATION = "textures/gui/sprites/enchanting_slot_disabled.png";
    public static final String C_SCROLLER_SPRITE_LOCATION = "container/loom/scroller";
    public static final String C_SCROLLER_DISABLED_SPRITE_LOCATION = "container/loom/scroller_disabled";
    // --------------------------------- VEINMINER
    public static final String C_SET_BLOCK_AIR_REQUEST = "set-block-air-request";
    public static final String C_INTERACT_ENTITY_REQUEST = "interact-entity-request";
    public static final String C_VEINMINER_BREAK_REQUEST = "veinminer-break-request";
    public static final String C_VEINMINER_SERVER_BLOCK_BREAK_REQUEST = "veinminer-server-block-break-request";
    // --------------------------------- KeyMappings
    public static final String KEY_CATEGORY_SMACKSUTIL = "key.category.smacksutil";
    public static final String KEY_SMACKSUTIL_VEINACTIVATE = "key.smacksutil.veinactivate";
    public static final String KEY_SMACKSUTIL_VEINPREVIEW = "key.smacksutil.veinpreview";
    public static final String KEY_SMACKSUTIL_FASTPLACE = "key.smacksutil.fastplace";
    public static final String KEY_SMACKSUTIL_EXACTMATCH = "key.smacksutil.exactmatch";
    public static final String KEY_SMACKSUTIL_OPEN_BACKPACK = "key.smacksutil.open_backpack";
    public static final String KEY_SMACKSUTIL_TOGGLE_MAGNET = "key.smacksutil.toggle_magnet";
    public static final String KEY_SMACKSUTIL_TOGGLE_LIGHT_WAND = "key.smacksutil.toggle_light_wand";
}