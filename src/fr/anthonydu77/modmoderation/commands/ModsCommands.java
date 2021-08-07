package fr.anthonydu77.modmoderation.commands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.anthonydu77.modmoderation.Main;
import fr.anthonydu77.modmoderation.managers.lang.Lang;
import fr.anthonydu77.modmoderation.managers.PlayerManager;
import fr.anthonydu77.modmoderation.managers.lang.LangValue;
import fr.anthonydu77.modmoderation.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthonydu77 08/11/2020 inside the package - fr.anthonydu77.modmoderation.commands
 */

public class ModsCommands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.SERVEUR_NAME_CONSOLE.get() + Lang.MOD_TO_CONSOLE.get());
            return false;
        }

        Player player = (Player) sender;

        /* Commande /mod */
        if (label.equalsIgnoreCase("mod")) {
            if (!(player.hasPermission(Lang.PERMISSION_MOD.get()))) {
                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.NO_PERMISSION.get());
                return false;
            }

            if (PlayerManager.isInModerationMod(player)) {
                Main.getInstance().getModerateur().remove(player.getUniqueId());
                player.getInventory().clear();
                PlayerManager pm = PlayerManager.getFromPlayer(player);
                pm.giveInventory();
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setGameMode(GameMode.SURVIVAL);

                player.sendTitle("§l§c~ §l§fModModeration §l§c~", "Off", 20, 20, 20);
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 0.5F);

                Bukkit.dispatchCommand(player, "sv off");
                FPlayer fPlayers = FPlayers.getInstance().getByPlayer(player);
                fPlayers.setIsAdminBypassing(false);

                ((Player) sender).removePotionEffect(PotionEffectType.NIGHT_VISION);

                player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.MODERATION_MOD_OFF.get());

                FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
                Faction faction = fPlayer.getFaction();
                Bukkit.broadcastMessage(Lang.PLAYER_JOIN_EVENT.get()
                        .replace(LangValue.FACTION.toName(), faction.getTag(fPlayer))
                        .replace(LangValue.PLAYER.toName(), player.getName()));

                Main.getInstance().getLogger().info(player.getName() + " use " + label + " off"); //Log
                return false;
            }
            Main.getInstance().getModerateur().add(player.getUniqueId());
            PlayerManager pm = pm = new PlayerManager(player);
            pm.init();
            pm.saveInventoty();
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setGameMode(GameMode.CREATIVE);

            ItemBuilder randomtp = new ItemBuilder(Material.COMPASS).setName("§c~ §fRandomTp §c~").setLore(ChatColor.GOLD + "Clique droit", ChatColor.GOLD + "Pour ce téleporter aleatoirement");
            ItemBuilder information = new ItemBuilder(Material.ENCHANTED_BOOK).setName("§c~ §fInformation §c~").setLore(ChatColor.GOLD + "Clique droit sur un joueur", ChatColor.GOLD + "Pour voir c'est information");
            ItemBuilder knockback = new ItemBuilder(Material.STICK).setName("§c~ §fKnockBack §c~").setLore(ChatColor.GOLD + "Clique gauche sur un joueur", ChatColor.GOLD + "Pour test c'est kb").addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
            ItemBuilder vanish = new ItemBuilder(Material.LIME_DYE).setName("§c~ §fVanish §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "ce metre en vanish");
            ItemBuilder invsee = new ItemBuilder(Material.CHEST).setName("§c~ §fInventaire §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "voir l'inventaire du joueur");
            ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§c~ §fFreeze §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "freeze un joueur");
            //ItemBuilder gamemode = new ItemBuilder(Material.CLOCK).setName("§c~ §fGamemode Changeur §c~").setLore(ChatColor.GOLD + "Clique droit pour", ChatColor.GOLD + "chnager de gamemode");

            player.getInventory().setItem(0, randomtp.toItemStack());
            player.getInventory().setItem(1, information.toItemStack());
            player.getInventory().setItem(2, knockback.toItemStack());
            //player.getInventory().setItem(4, gamemode.toItemStack());
            player.getInventory().setItem(6, vanish.toItemStack());
            player.getInventory().setItem(7, invsee.toItemStack());
            player.getInventory().setItem(8, freeze.toItemStack());

            Bukkit.dispatchCommand(player, "sv on");
            FPlayer fPlayers = FPlayers.getInstance().getByPlayer(player);
            fPlayers.setIsAdminBypassing(true);
            ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));

            player.sendTitle("§l§c~ §l§fModModeration §l§c~", "On", 20, 20, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0F, 0.5F);

            player.sendMessage(Lang.SERVEUR_NAME.get() + Lang.MODERATION_MOD_ON.get());

            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            Faction faction = fPlayer.getFaction();
            Bukkit.broadcastMessage(Lang.PLAYER_LEAVE_EVENT.get()
                    .replace(LangValue.FACTION.toName(), faction.getTag())
                    .replace(LangValue.PLAYER.toName(), player.getName()));


            Main.getInstance().getLogger().info(player.getName() + " use " + label + " on"); //Log
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> result = new ArrayList<>();
        result.add("");
        return result;
    }
}