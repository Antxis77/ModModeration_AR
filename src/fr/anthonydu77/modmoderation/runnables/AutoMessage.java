package fr.anthonydu77.modmoderation.runnables;

import fr.anthonydu77.modmoderation.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AutoMessage extends BukkitRunnable {

    private List<String> messages = Main.getInstance().getConfig("messages").getStringList("messages");
    private int i = 0;

    @Override
    public void run() {

        int msg = Main.getInstance().getConfig("messages").getStringList("messages").size();

        if (Main.getInstance().getConfig("messages").getStringList("messages").isEmpty()) {
            Main.getInstance().getLogger().info("No auto message find");
        } else {
            if (i == msg) {
                i = 0;
            } else {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', messages.get(i)));

                i++;
            }
        }
    }
}
