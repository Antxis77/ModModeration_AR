package fr.anthonydu77.modmoderation.commands;

import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthonydu77 18/11/2020 inside the package - fr.anthonydu77.modmoderation.commands
 */

public class OtherCommands implements CommandExecutor , TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.SERVEUR_NAME_CONSOLE.get() + Lang.MOD_TO_CONSOLE.get());
            return false;
        }

        Player player = (Player) sender;

        /* Commande /nightvision */
        if (label.equalsIgnoreCase("nightvision")) {
            if (!sender.hasPermission(Lang.PERMISSION_NIGHTVISION.get())) {
                sender.sendMessage(Lang.NO_PERMISSION.get());
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(Lang.SERVEUR_NAME_CONSOLE.get() + Lang.MOD_TO_CONSOLE.get());
                return false;
            }

            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                sender.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NIGHTVISION_OFF.get());
                ((Player) sender).removePotionEffect(PotionEffectType.NIGHT_VISION);
            } else {
                sender.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NIGHTVISION_ON.get());
                ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
            }
        }

        if (label.equalsIgnoreCase("gg")) {
            if (!(player.hasPermission(Lang.PERMISSION_MTP.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)){
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }

            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.setGameMode(GameMode.SPECTATOR);
            } else if (player.getGameMode().equals(GameMode.SPECTATOR)) {
                player.setGameMode(GameMode.SURVIVAL);
            } else {
                player.sendMessage("&cERROR Gamemode unknown !");
            }

        }

        /* Commande /discord */
        if (label.equalsIgnoreCase("discord")){
            player.sendMessage(Lang.STAFF.get() + Lang.STAFF_DISCORD.get());
        }

        /* Commande /ModModerationReload */
        if (label.equalsIgnoreCase("ModModerationReload")){
            if (!(player.hasPermission(Lang.PERMISSION_RELOAD.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            player.sendMessage(Lang.SERVEUR_RELOAD_START.get());
            Main.getInstance().reloadConfig();
            player.sendMessage(Lang.SERVEUR_RELOAD_END.get());
        }
        /* Commande /staffchat */
        if (label.equalsIgnoreCase("staffchat")){
            if (!(player.hasPermission(Lang.PERMISSION_RELOAD.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }

            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.hasPermission(Lang.PERMISSION_STAFFCHAT.get())) {
                    players.sendMessage(Lang.STAFF.get() + ChatColor.WHITE + player.getName() + " : " + "en dev");
                }
            }
        }


        /* Commande /chatlock */
        if (label.equalsIgnoreCase("chatlock")){
            if (!(player.hasPermission(Lang.PERMISSION_CHATLOCK.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (!PlayerManager.isInModerationMod(player)){
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            Main.getInstance().setChatlock(!Main.getInstance().isChatlock());
        }

        /* Commande /modlist */
        if (label.equalsIgnoreCase("modlist")){
            if (!(player.hasPermission(Lang.PERMISSION_MOD.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            int t = 0;
            player.sendMessage(Lang.LIST_MOD.get());
            for (Player players : Bukkit.getOnlinePlayers()){
                if (PlayerManager.isInModerationMod(players)){
                    player.sendMessage(">> " + players.getName());
                    t++;
                }
            }
            if (t == 0){
                player.sendMessage(">> Aucun Staff en /mod <<");
            }

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> result = new ArrayList<>();
        result.add("");
        return result;
    }
}