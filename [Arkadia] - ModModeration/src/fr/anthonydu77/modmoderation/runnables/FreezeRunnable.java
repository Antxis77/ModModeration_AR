package fr.anthonydu77.modmoderation.runnables;

import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by Anthonydu77 12/11/2020 inside the package - fr.anthonydu77.modmoderation.runnables
 */

public class FreezeRunnable extends BukkitRunnable {

    private int t = 0;

    @Override
    public void run() {
        for (UUID uuid : Main.getInstance().getFreezedPlayers().keySet()){
            Player player = Bukkit.getPlayer(uuid);
            if (player != null){
                player.teleport(Main.getInstance().getFreezedPlayers().get(uuid));
                player.sendTitle("§l§c~ §l§fVerification §l§c~", Lang.FREEZE_MSG.get(), 20, 20, 20);
                if (Main.getInstance().getT() == 5){
                    t = 0;
                    Main.getInstance().setT(0);
                }
            }
        }

        t++;
        Main.getInstance().setT(t);
    }
}
