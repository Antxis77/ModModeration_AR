package fr.anthonydu77.modmoderation.commands;

import fr.anthonydu77.modmoderation.managers.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthonydu77 15/12/2020 inside the package - fr.anthonydu77.modmoderation.commands
 */

public class ClearLagCommands implements CommandExecutor, TabCompleter {

    List<String> arguments = new ArrayList<String>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.SERVEUR_NAME_CONSOLE.get() + Lang.MOD_TO_CONSOLE.get());
            return false;
        }

        Player player = (Player) sender;

        /* Commande /clearlag */
        if (label.equalsIgnoreCase("clearlag")) {
            if (!(player.hasPermission(Lang.PERMISSION_CLEAR_LAG.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }
            if (args.length != 1) {
                sender.sendMessage(Lang.CLEARLAG_PRESET_INFO.get());
                sender.sendMessage(Lang.EMPTY.get());
                sender.sendMessage(Lang.CLEARLAG_MESSAGE_INFO.get());
                sender.sendMessage(Lang.CLEARLAG_MESSAGE_CLEAR.get());
                sender.sendMessage(Lang.EMPTY.get());
                sender.sendMessage(Lang.CLEARLAG_PRESET_ENDS.get());
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("info")) {
                    Runtime run = Runtime.getRuntime();
                    List<Entity> entity = player.getWorld().getEntities();
                    int count = 0;
                    for (Entity current : entity) {
                        if (current instanceof Item) {
                            count++;
                        }
                    }
                    sender.sendMessage(Lang.CLEARLAG_PRESET_INFO.get());
                    sender.sendMessage(Lang.EMPTY.get());
                    sender.sendMessage(Lang.CLEARLAG_INFO_RAM_UTILS.get() + ((run.totalMemory() - run.freeMemory()) / 1048576) + Lang.CLEARLAG_INFO_RAM_UNITS.get());
                    sender.sendMessage(Lang.CLEARLAG_INFO_RAM_DISPO.get() + ((run.maxMemory() / 1048576) - ((run.totalMemory() - run.freeMemory()) / 1048576)) + Lang.CLEARLAG_INFO_RAM_UNITS.get());
                    sender.sendMessage(Lang.CLEARLAG_INFO_RAM_MAX.get() + (run.maxMemory() / 1048576) + Lang.CLEARLAG_INFO_RAM_UNITS.get());
                    sender.sendMessage(Lang.CLEARLAG_INFO_ITEMS.get() + count + Lang.CLEARLAG_ITEMS.get());
                    sender.sendMessage(Lang.EMPTY.get());
                    sender.sendMessage(Lang.CLEARLAG_PRESET_ENDS.get());
                }
                if (args[0].equalsIgnoreCase("force")) {
                    List<Entity> entity = player.getWorld().getEntities();
                    int count = 0;
                    for (Entity current : entity) {
                        if (current instanceof Item) {
                            count++;
                            current.remove();
                        }
                    }
                    Bukkit.broadcastMessage(Lang.CLEARLAG_PRESET.get() + Lang.CLEARLAG_CLEAR.get() + count + Lang.CLEARLAG_ITEMS.get());
                }
            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (arguments.isEmpty()) {
            arguments.add("info");
            arguments.add("force");
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(a);
            }
            return result;
        }

        List<String> results = new ArrayList<>();
        results.add("");
        return results;
    }

}
