package fr.anthonydu77.modmoderation.managers;

import fr.anthonydu77.modmoderation.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Anthonydu77 08/11/2020 inside the package - fr.anthonydu77.modmoderation.managers
 */

public class PlayerManager {

    private Player player;
    private ItemStack[] items = new ItemStack[41];

    public PlayerManager(Player player) {
        this.player = player;
    }

    public void init() {
        Main.getInstance().getPlayers().put(player.getUniqueId(), this);
    }

    public void destroy() {
        Main.getInstance().getPlayers().remove(player.getUniqueId());
    }

    public static PlayerManager getFromPlayer(Player player) {
        return Main.getInstance().getPlayers().get(player.getUniqueId());
    }

    public static boolean isInModerationMod(Player player) {
        return Main.getInstance().getModerateur().contains(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void saveInventoty() {
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null) {
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();
        items[40] = player.getInventory().getItemInOffHand();

        player.getInventory().clear();
    }

    public void giveInventory() {
        player.getInventory().clear();
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = items[slot];
            if (item != null) {
                player.getInventory().setItem(slot, item);
            }
        }
        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
        player.getInventory().setItemInOffHand(items[40]);
        destroy();
    }
}
