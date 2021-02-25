package fr.anthonydu77.modmoderation.managers.lang;

import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.ModFile;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anthonydu77 13/12/2020 inside the package - fr.anthonydu77.modmoderation.managers.lang
 */

public enum Lang {
    EMPTY,
    SERVEUR_NAME,
    SERVEUR_NAME_CONSOLE,

    MTP_FORMAT,
    MTPH_FORMAT,
    VERIF_FORMAT,
    LOOKUP_FORMAT,

    SERVEUR_RELOAD_START,
    SERVEUR_RELOAD_END,

    NIGHTVISION_OFF,
    NIGHTVISION_ON,

    LIST_MOD,

    STAFF,
    STAFF_VERIF,
    STAFF_DISCORD,

    MOD_TO_CONSOLE,
    NO_PERMISSION,

    NO_PLAYER,
    NO_PLAYER_TELEPORT,

    TELEPORT_SUCCES,

    MODERATION_MOD_ON,
    MODERATION_MOD_OFF,

    CHATLOCK_ON_PLAYER,
    CHATLOCK_ON,
    CHATLOCK_OFF,

    FREEZE_MSG,
    FREEZE_MSG_ON_TARGET,
    FREEZE_MSG_ON_MOD,
    FREEZE_MSG_OFF_TARGET,
    FREEZE_MSG_OFF_MOD,

    PERMISSION_MOD,
    PERMISSION_MTP,
    PERMISSION_MTPH,
    PERMISSION_VERIF,
    PERMISSION_CLEAR_LAG,
    PERMISSION_CHATLOCK,
    PERMISSION_NIGHTVISION,
    PERMISSION_RELOAD,
    PERMISSION_LOOKUP,
    PERMISSION_STAFFCHAT,

    CLEARLAG_PRESET,
    CLEARLAG_PRESET_INFO,
    CLEARLAG_PRESET_ENDS,

    CLEARLAG_MESSAGE_INFO,
    CLEARLAG_MESSAGE_CLEAR,
    CLEARLAG_CLEAR,
    CLEARLAG_ITEMS,

    CLEARLAG_INFO_RAM_UTILS,
    CLEARLAG_INFO_RAM_DISPO,
    CLEARLAG_INFO_RAM_MAX,
    CLEARLAG_INFO_ITEMS,
    CLEARLAG_INFO_RAM_UNITS,

    PLAYER_JOIN_EVENT,
    PLAYER_LEAVE_EVENT,
    PLAYER_DEAD_EVENT,
    PLAYER_KILL_EVENT,
    PLAYER_CHAT_EVENT,
    PLAYER_ENEMY_CHAT_EVENT,
    PLAYER_ALLY_CHAT_EVENT;

    private static final Map<Lang, String> VALUES = new HashMap<>();

    static {
        for (Lang lang : values()){
            VALUES.put(lang, lang.getFromFile());
        }
        Main.getInstance().getLogger().info("Lang file read successfuly !");
    }

    public String get(){
        return  VALUES.get(this);
    }

    public static String getPrefix(){
        return SERVEUR_NAME.get() + ChatColor.RESET + " ";
    }

    private String getFromFile(){
        FileConfiguration config = ModFile.LANG.getConfig();
        String key = name().toLowerCase().replace('_', '-');
        String value = config.getString(key);

        if (value== null){
            value = "";
        }

        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
