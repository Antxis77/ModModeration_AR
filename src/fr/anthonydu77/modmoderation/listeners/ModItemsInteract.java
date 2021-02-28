package fr.anthonydu77.modmoderation.listeners;

import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anthonydu77 08/11/2020 inside the package - fr.anthonydu77.modmoderation.listeners
 */

public class ModItemsInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!PlayerManager.isInModerationMod(player)) return;
        if (!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();
        e.setCancelled(true);

        switch (player.getInventory().getItemInHand().getType()) {
            /**
             * Voir l'inventaire
             */
            case CHEST:
                Inventory inv = Bukkit.createInventory(null, 5 * 9, target.getName() + " > Inventaire");

                for (int i = 0; i < 36; i++) {
                    if (target.getInventory().getItem(i) != null) {
                        inv.setItem(i, target.getInventory().getItem(i));
                    }
                }

                inv.setItem(36, target.getInventory().getHelmet());
                inv.setItem(37, target.getInventory().getChestplate());
                inv.setItem(38, target.getInventory().getLeggings());
                inv.setItem(39, target.getInventory().getBoots());
                inv.setItem(40, target.getInventory().getItemInOffHand());

                player.openInventory(inv);
                break;

            /**
             * Voir les informations
             */
            case ENCHANTED_BOOK:
                player.sendMessage("En Dev");
                break;

            /**
             * Freeze
             */
            case PACKED_ICE:
                if (Main.getInstance().getT() != 0) {
                    player.sendMessage(" ");
                    Main.getInstance().setT(0);
                } else {
                    if (Main.getInstance().getFreezedPlayers().containsKey(target.getUniqueId())) {
                        Main.getInstance().getFreezedPlayers().remove(target.getUniqueId());
                        target.sendMessage(Lang.FREEZE_MSG_OFF_TARGET.get());
                        player.sendMessage(Lang.FREEZE_MSG_OFF_MOD.get() + target.getName());
                        Main.getInstance().getLogger().info(player.getName() + " use " + "UnFreeze" + " on " + target.getName()); //Log
                    } else {
                        Main.getInstance().getFreezedPlayers().put(target.getUniqueId(), target.getLocation());
                        target.sendMessage(Lang.FREEZE_MSG_ON_TARGET.get());
                        player.sendMessage(Lang.FREEZE_MSG_ON_MOD.get() + target.getName());
                        Main.getInstance().getLogger().info(player.getName() + " use " + "Freeze" + " on " + target.getName()); //Log
                    }
                }

                break;

            default:
                break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!PlayerManager.isInModerationMod(player)) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;

        switch (player.getInventory().getItemInHand().getType()) {
            /**
             * Téléportation aléatoire
             */
            case COMPASS:
                List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
                list.remove(player);

                if (list.size() == 0) {
                    player.sendMessage(Lang.NO_PLAYER_TELEPORT.get());
                    return;
                }

                Player target = list.get(new Random().nextInt(list.size()));
                player.teleport(target.getLocation());
                player.sendMessage("§aVous avez été téléporté à §e" + target.getName());
                break;

            /**
             * Vanish
             */
            case LIME_DYE:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                Bukkit.dispatchCommand(player, "sv off");
                player.sendMessage("§aVous êtes à présent visible !");
                ItemBuilder vanish = new ItemBuilder(Material.GRAY_DYE).setName("§c~ §fVanish §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "ce metre en vanish");
                player.getInventory().setItem(6, vanish.toItemStack());
                break;

            case GRAY_DYE:
                mod = PlayerManager.getFromPlayer(player);
                Bukkit.dispatchCommand(player, "sv on");
                player.sendMessage("§bVous êtes à présent invisible !");
                vanish = new ItemBuilder(Material.LIME_DYE).setName("§c~ §fVanish §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "ce metre en vanish");
                player.getInventory().setItem(6, vanish.toItemStack());
                break;

            /**
             * Gamemode
             */
            case CLOCK:
                if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.setGameMode(GameMode.SPECTATOR);
                } else {
                    player.setGameMode(GameMode.SURVIVAL);
                }
                break;

            default:
                break;
        }
    }
}
