package fr.anthonydu77.modmoderation;

import fr.anthonydu77.modmoderation.commands.ClearLagCommands;
import fr.anthonydu77.modmoderation.commands.ModsCommands;
import fr.anthonydu77.modmoderation.commands.OtherCommands;
import fr.anthonydu77.modmoderation.commands.TeleportsVerifCommands;
import fr.anthonydu77.modmoderation.managers.config.Settings;
import fr.anthonydu77.modmoderation.listeners.*;
import fr.anthonydu77.modmoderation.managers.ModFile;
import fr.anthonydu77.modmoderation.runnables.FreezeRunnable;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import fr.anthonydu77.modmoderation.runnables.AutoMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Anthonydu77 08/11/2020 inside the package - fr.anthonydu77.modmoderation
 */

public class Main extends JavaPlugin {

    private static Main instance;
    private ArrayList<UUID> moderateur;
    private Map<UUID, PlayerManager> players;
    private Map<UUID, Location> freezedPlayers;
    private int t;
    private boolean chatlock;
    private Settings settings;

    public static PluginManager pm;
    public static HashMap<Player, Integer> vanishList;

    private static final Logger log = Logger.getLogger("Minecraft");

    public static String getLog_Prefix() {
        return "[Arkadia] - ";
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        log.info(getLog_Prefix() + "---------- " + getDescription().getFullName() + " ----------");
        log.info(getLog_Prefix() + "Starting " + getDescription().getName() + " ...");
        log.info(getLog_Prefix() + "Author : " + getDescription().getAuthors());
        log.info(getLog_Prefix() + "Description : " + getDescription().getDescription());
        log.info(getLog_Prefix() + "Version : " + getDescription().getVersion());
        log.info(getLog_Prefix() + "If you have any problem contact me at discord : Antho77_#1536");

        instance = this;
        registerYamls();
        registerEvents();
        regsiterCommands();
        registerVariables();
        registerRunnables();

        log.info(getLog_Prefix() + getDescription().getName() + " status is ready");
        log.info(getLog_Prefix() + "---------- " + getDescription().getFullName() + " ----------");
    }

    @Override
    public void reloadConfig() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            PlayerManager pm = PlayerManager.getFromPlayer(players);
            if (PlayerManager.isInModerationMod(players)) {
                Main.getInstance().getModerateur().remove(players.getUniqueId());
                players.getInventory().clear();
                pm.giveInventory();
            }
        }
    }

    @Override
    public void onDisable() {
        log.info(getLog_Prefix() + "---------- " + getDescription().getFullName() + " ----------");
        log.info(getLog_Prefix() + getDescription().getName() + " is shutting off");
        log.info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!(players == null)) {
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if (PlayerManager.isInModerationMod(players)) {
                    Main.getInstance().getModerateur().remove(players.getUniqueId());
                    players.getInventory().clear();
                    pm.giveInventory();
                }
            }
        }

        log.info(getLog_Prefix() + getDescription().getName() + " status is off");
        log.info(getLog_Prefix() + "---------- " + getDescription().getFullName() + " ----------");
    }

    private void registerVariables() {
        moderateur = new ArrayList<>();
        players = new HashMap<>();
        freezedPlayers = new HashMap<>();
        t = 0;
        pm = Bukkit.getPluginManager();
        chatlock = false;
        vanishList = new HashMap<>();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
        pm.registerEvents(new PlayerEvent(), this);

        log.info(getLog_Prefix() + "Register Events is done !");
    }

    private void regsiterCommands() {
        /* Class : ModsCommands */
        Objects.requireNonNull(getCommand("mod")).setExecutor(new ModsCommands());
        Objects.requireNonNull(getCommand("mod")).setTabCompleter(new ModsCommands());

        /* Class : ClearLagCommands */
        Objects.requireNonNull(getCommand("clearlag")).setExecutor(new ClearLagCommands());
        Objects.requireNonNull(getCommand("clearlag")).setTabCompleter(new ClearLagCommands());

        /* Class : TeleportsVerifCommands */
        Objects.requireNonNull(getCommand("mtp")).setExecutor(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("mtp")).setTabCompleter(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("mtph")).setExecutor(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("mtph")).setTabCompleter(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("verif")).setExecutor(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("verif")).setTabCompleter(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("lookup")).setExecutor(new TeleportsVerifCommands());
        Objects.requireNonNull(getCommand("lookup")).setTabCompleter(new TeleportsVerifCommands());

        /* Class : OtherCommands */
        Objects.requireNonNull(getCommand("discord")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("discord")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("gg")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("gg")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("modlist")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("modlist")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("nightvision")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("nightvision")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("chatlock")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("chatlock")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("staffchat")).setTabCompleter(new OtherCommands());
        Objects.requireNonNull(getCommand("modmoderationreload")).setExecutor(new OtherCommands());
        Objects.requireNonNull(getCommand("modmoderationreload")).setTabCompleter(new OtherCommands());

        log.info(getLog_Prefix() + "Register Commands is done !");
    }

    private void registerYamls() {
        ModFile lang = ModFile.LANG;
        lang.create(getLogger());
        try (final Reader reader = Files.newBufferedReader(lang.getFile().toPath(), StandardCharsets.UTF_8)) {
            final Yaml yaml = new Yaml(new CustomClassLoaderConstructor(getClassLoader()));
            yaml.setBeanAccess(BeanAccess.FIELD);
            //langs = yaml.loadAs(reader, Lang.class);
            log.info(getLog_Prefix() + "Lang file read successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModFile config = ModFile.CONFIG;
        config.create(getLogger());
        try (final Reader reader = Files.newBufferedReader(config.getFile().toPath(), StandardCharsets.UTF_8)) {
            final Yaml yaml = new Yaml(new CustomClassLoaderConstructor(getClassLoader()));
            yaml.setBeanAccess(BeanAccess.FIELD);
            settings = yaml.loadAs(reader, Settings.class);
            getLogger().info("Configuration file read successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModFile messages = ModFile.MESSAGES;
        messages.create(getLogger());
        try (final Reader reader = Files.newBufferedReader(messages.getFile().toPath(), StandardCharsets.UTF_8)) {
            final Yaml yaml = new Yaml(new CustomClassLoaderConstructor(getClassLoader()));
            yaml.setBeanAccess(BeanAccess.FIELD);
            //settings = yaml.loadAs(reader, Settings.class);
            getLogger().info("Messages file read successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info(getLog_Prefix() + "Register Yaml is done !");
    }

    private void registerRunnables() {
        if (getSettings().isMessage()) {
            new AutoMessage().runTaskTimer(this, 10 * 20L, instance.getSettings().getPeriod() * 20L);
        }
        new FreezeRunnable().runTaskTimer(this, 0, 20);

        log.info(getLog_Prefix() + "Register Runnables is done !");
    }

    private void createFile(String fileName) {
        if (!(getDataFolder().exists())) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), fileName + ".yml");
        if (!(file.exists())) {
            try {
                file.createNewFile();
                if (fileName.equalsIgnoreCase("message")) {
                    FileConfiguration config = getConfig("message");
                    config.set("message", Arrays.asList("&6Test"));
                    save(file, config);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String fileName) {
        return new File(getDataFolder(), fileName + ".yml");
    }

    public FileConfiguration getConfig(String fileName) {
        return YamlConfiguration.loadConfiguration(getFile(fileName));
    }

    public void save(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UUID> getModerateur() {
        return moderateur;
    }

    public Map<UUID, PlayerManager> getPlayers() {
        return players;
    }

    public Map<UUID, Location> getFreezedPlayers() {
        return freezedPlayers;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setChatlock(boolean chatlock) {
        this.chatlock = chatlock;
        if (chatlock) {
            Bukkit.broadcastMessage(Lang.SERVEUR_NAME.get() + Lang.CHATLOCK_OFF.get());
        } else {
            Bukkit.broadcastMessage(Lang.SERVEUR_NAME.get() + Lang.CHATLOCK_ON.get());
        }
    }

    public boolean isChatlock() {
        return chatlock;
    }

    public Settings getSettings() {
        return settings;
    }
}
