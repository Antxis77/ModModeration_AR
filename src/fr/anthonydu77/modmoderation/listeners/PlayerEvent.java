package fr.anthonydu77.modmoderation.listeners;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.ChatMode;
import com.massivecraft.factions.struct.Relation;
import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.managers.lang.LangValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

/**
 * Created by Anthonydu77 09/11/2020 inside the package - fr.anthonydu77.modmoderation.listeners
 */

public class PlayerEvent implements Listener {
    final private Main instace = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (instace.getSettings().isChat()) {
            Player player = e.getPlayer();
            if (e.getMessage().startsWith("!") && player.hasPermission(Lang.PERMISSION_STAFFCHAT.get())) {
                e.setCancelled(true);
                Bukkit.getOnlinePlayers().stream().filter(p -> p.hasPermission(Lang.PERMISSION_MOD.get())).forEach(p -> {
                    p.sendMessage(Lang.STAFF.get() + ChatColor.WHITE + player.getName() + " : " + e.getMessage().substring(1));
                });
                return;
            }
            if (PlayerManager.isInModerationMod(player)) {
                e.setCancelled(true);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(ChatColor.DARK_RED + "Staff" + " §r| " + player.getName() + " >> " + e.getMessage());
                }
                return;
            }
            if (Main.getInstance().isChatlock()) {
                e.setCancelled(true);
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.CHATLOCK_ON_PLAYER.get());
            }


            FPlayer fPlayerx = FPlayers.getInstance().getByPlayer(player);
            Faction factionx = fPlayerx.getFaction();
            if (fPlayerx.getChatMode() == ChatMode.ALLIANCE || fPlayerx.getChatMode() == ChatMode.TRUCE || fPlayerx.getChatMode() == ChatMode.FACTION || fPlayerx.getChatMode() == ChatMode.MOD) {
                return;
            }

            e.setCancelled(true);
            for (Player players : Bukkit.getOnlinePlayers()) {
                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
                Faction faction = fPlayer.getFaction();

                FPlayer fPlayers = FPlayers.getInstance().getByPlayer(players);
                Faction factions = fPlayers.getFaction();

                // Relation du player qui parle et recois
                Relation relation = faction.getRelationTo(factions);
                if (relation.isAlly()) {

                    players.sendMessage(Lang.PLAYER_ALLY_CHAT_EVENT.get()
                            .replace(LangValue.FACTION.toName(), faction.getTag())
                            .replace(LangValue.PLAYER.toName(), player.getName()) + e.getMessage());
                } else if (relation.isNeutral()) {

                    players.sendMessage(Lang.PLAYER_CHAT_EVENT.get()
                            .replace(LangValue.FACTION.toName(), faction.getTag())
                            .replace(LangValue.PLAYER.toName(), player.getName()) + e.getMessage());
                } else if (relation.isEnemy()) {

                    players.sendMessage(Lang.PLAYER_ENEMY_CHAT_EVENT.get()
                            .replace(LangValue.FACTION.toName(), faction.getTag())
                            .replace(LangValue.PLAYER.toName(), player.getName()) + e.getMessage());
                } else if (relation.isMember()) {

                    players.sendMessage(Lang.PLAYER_MEMBER_CHAT_EVENT.get()
                            .replace(LangValue.FACTION.toName(), faction.getTag())
                            .replace(LangValue.PLAYER.toName(), player.getName()) + e.getMessage());

                } else {
                    players.sendMessage(Lang.PLAYER_CHAT_EVENT.get()
                            .replace(LangValue.FACTION.toName(), faction.getTag())
                            .replace(LangValue.PLAYER.toName(), player.getName()) + e.getMessage());
                }
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) throws InterruptedException {
        Player player = e.getPlayer();
        // Log cord on teleport from
        Main.getInstance().getLogger().info(player.getDisplayName() + " teleport from : X=" + e.getFrom().getBlockX() +
                " Y=" + e.getFrom().getBlockY() + " Z=" + e.getFrom().getBlockZ() + " World" + player.getWorld());

        // Log cord on teleport to
        Main.getInstance().getLogger().info(player.getDisplayName() + " teleport to : X=" + Objects.requireNonNull(e.getTo()).getBlockX() +
                " Y=" + e.getTo().getBlockY() + " Z=" + e.getTo().getBlockZ() + " World" + player.getWorld());
    }

    public void onMove(PlayerLocaleChangeEvent e) {
        Player player = e.getPlayer();

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (instace.getSettings().isJoin()) {
            Player player = e.getPlayer();
            // Log cord on join
            Main.getInstance().getLogger().info(player.getName() + " join the game at : X=" + player.getLocation().getBlockX() +
                    " Y=" +player.getLocation().getBlockY() + " Z=" + player.getLocation().getBlockZ() + " World" + player.getWorld());

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            Faction faction = fPlayer.getFaction();
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setHealth(20);
            player.setFoodLevel(20);
            e.setJoinMessage(Lang.PLAYER_JOIN_EVENT.get()
                    .replace(LangValue.FACTION.toName(), faction.getTag(fPlayer))
                    .replace(LangValue.PLAYER.toName(), player.getName()));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (instace.getSettings().isLeave()) {
            Player player = e.getPlayer();
            // Log cord on left
            Main.getInstance().getLogger().info(player.getName() + " left the game at : X=" + player.getLocation().getBlockX() +
                    " Y=" +player.getLocation().getBlockY() + " Z=" + player.getLocation().getBlockZ() + " World" + player.getWorld());

            PlayerManager pm = PlayerManager.getFromPlayer(player);
            if (PlayerManager.isInModerationMod(player)) {
                Main.getInstance().getModerateur().remove(player.getUniqueId());
                player.getInventory().clear();
                pm.giveInventory();
                Main.getInstance().getLogger().info(player.getName() + " was left in moderation mod");
            }
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            Faction faction = fPlayer.getFaction();
            e.setQuitMessage(Lang.PLAYER_LEAVE_EVENT.get()
                    .replace(LangValue.FACTION.toName(), faction.getTag())
                    .replace(LangValue.PLAYER.toName(), player.getName()));
        }
    }

    @EventHandler
    public void onDead(PlayerDeathEvent e) {
        if (instace.getSettings().isDead()) {
            Player player = e.getEntity().getPlayer();
            Player killer = player.getKiller();
            if (killer == null) {
                e.setDeathMessage(Lang.PLAYER_DEAD_EVENT.get()
                        .replace(LangValue.PLAYER.toName(), player.getName()));
            } else {
                e.setDeathMessage(Lang.PLAYER_KILL_EVENT.get()
                        .replace(LangValue.KILLER.toName(), killer.getName())
                        .replace(LangValue.PLAYER.toName(), player.getName()));
            }
        }
    }

    public void onChangeDim(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setGameMode(GameMode.SURVIVAL);
        } else {
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    /*@EventHandler
    public void onSneak(PlayerSwapHandItemsEvent e) {
        Player player = e.getPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            if (e.isAsynchronous()) {
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
            if (e.isCancelled()){
                if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
            }
        }
    }*/
}
