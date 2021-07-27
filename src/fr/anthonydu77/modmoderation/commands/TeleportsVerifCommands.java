package fr.anthonydu77.modmoderation.commands;

import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthonydu77 15/12/2020 inside the package - fr.anthonydu77.modmoderation.commands
 */

public class TeleportsVerifCommands implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.SERVEUR_NAME_CONSOLE.get() + Lang.MOD_TO_CONSOLE.get());
            return false;
        }

        Player player = (Player) sender;


        /* Commande /mtp */
        if (label.equalsIgnoreCase("mtp")) {
            if (!(player.hasPermission(Lang.PERMISSION_MTP.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (args.length != 1) {
                player.sendMessage(Lang.MTP_FORMAT.get());
                return false;
            }

            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) == null) {
                player.sendMessage(Lang.NO_PLAYER.get());
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            ((Player) sender).teleport(target);
            player.sendMessage(Lang.STAFF.get() + Lang.TELEPORT_SUCCES.get());
            Main.getInstance().getLogger().info(player.getName() + " use " + label + " on " + target.getName()); // Log

        }

        /* Commande /mtph */
        if (label.equalsIgnoreCase("mtph")) {
            if (!(player.hasPermission(Lang.PERMISSION_MTPH.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (args.length != 1) {
                player.sendMessage(Lang.MTPH_FORMAT.get());
                return false;
            }

            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) == null) {
                player.sendMessage(Lang.NO_PLAYER.get());
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            target.teleport(player);
            player.sendMessage(Lang.STAFF.get() + Lang.TELEPORT_SUCCES.get());
            Main.getInstance().getLogger().info(player.getName() + " use " + label + " on " + target.getName()); //Log
        }

        /* Commande /lookup */
        if (label.equalsIgnoreCase("lookup")) {
            if (!(player.hasPermission(Lang.PERMISSION_LOOKUP.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (args.length != 1) {
                player.sendMessage(Lang.LOOKUP_FORMAT.get());
                return false;
            }

            String targetName = args[0];

            if (Bukkit.getPlayer(targetName) == null) {
                player.sendMessage(Lang.NO_PLAYER.get());
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            player.sendMessage(Lang.STAFF.get() + targetName);
            player.sendMessage(ChatColor.GRAY + "UUID : " + target.getUniqueId());
            player.sendMessage(ChatColor.GRAY + "Health : " + target.getHealth() + " /20");
            player.sendMessage(ChatColor.GRAY + "Location : X=" + player.getLocation().getBlockX() +
                                                        " Y=" +player.getLocation().getBlockY() +
                                                        " Z=" + player.getLocation().getBlockZ() +
                                                        " World" + player.getWorld());
            player.sendMessage(ChatColor.GRAY + "Gamemode : " + target.getGameMode());
            player.sendMessage(ChatColor.GRAY + "Level : " + target.getLevel());
            player.sendMessage(ChatColor.GRAY + "Ip : " + target.getAddress());

            Main.getInstance().getLogger().info(player.getName() + " use " + label + " on " + target.getName()); //Log
        }

        /* Commande /verif */
        if (label.equalsIgnoreCase("verif")) {
            if (!(player.hasPermission(Lang.PERMISSION_VERIF.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (args.length != 1) {
                player.sendMessage(Lang.VERIF_FORMAT.get());
                return false;
            }

            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);
            target.sendTitle("§l§c~ §l§fVerification §l§c~", "Passe discord (/discord) dans les 5 prochaine minutes sinon ban", 20, 360, 20);
            player.sendMessage(Lang.STAFF.get() + Lang.STAFF_VERIF.get() + target.getName());
            Main.getInstance().getLogger().info(player.getName() + " use " + label + " on " + target.getName()); //Log
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {

            List<String> playerNames = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            for (int i = 0; i < players.length; i++) {
                playerNames.add(players[i].getName());
            }
            return playerNames;
        } else {
            List<String> result = new ArrayList<>();
            result.add("");
            return result;
        }
    }
}
