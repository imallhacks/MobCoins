package com.peaches.mobcoins;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

class Events implements org.bukkit.event.Listener {
    private static Main plugin;

    public Events(Main pl) {
        plugin = pl;
    }

    @EventHandler
    public void OnPlayerJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String player = p.getUniqueId().toString();
        CoinsAPI.createPlayer(player);
    }

    @EventHandler
    public void OnInvClick(@NotNull InventoryClickEvent e) {
        if (e.getInventory() != null) {
            if (e.getInventory().getTitle().equals(Utils.getTitle())) {
                Player p = (Player) e.getWhoClicked();
                for (int i = 0; i < 28; i++) {
                    if (e.getCurrentItem().equals(Utils.getItem(i, p))) {
                        String player = p.getUniqueId().toString();
                        if (CoinsAPI.getCoins(player) >= Utils.getPrice(i, p)) {
                            p.closeInventory();
                            CoinsAPI.removeCoins(player, Utils.getPrice(i, p));
                            p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Brought an Item From The Shop!");
                            java.util.List<String> command = plugin.getConfig().getStringList("Shop." + i + ".Commands");
                            for (String cmd : command) {
                                org.bukkit.Bukkit.getServer().dispatchCommand(org.bukkit.Bukkit.getServer().getConsoleSender(), cmd.replace("%player%", e.getWhoClicked().getName()));
                            }
                        } else {
                            p.closeInventory();
                            p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You dont have enough Mobcoins!");
                        }
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void OnEntityDeath(@NotNull EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            if (e.getEntityType() != null && e.getEntityType().getName() != null) {
                if (Utils.hasmob(e.getEntityType().getName().toUpperCase())) {
                    Random object = new Random();
                    int i = 1 + object.nextInt(100);
                    if (i <= Utils.getChange(e.getEntityType().getName().toUpperCase())) {
                        Player p = e.getEntity().getKiller();
                        if (p == null) return;
                        String player = p.getUniqueId().toString();
                        MobCoinsGiveEvent event = new MobCoinsGiveEvent(p, 1);
                        Bukkit.getPluginManager().callEvent(event);
                        if (event.getAmount() > 1) {
                            p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A " + e.getEntityType().getName() + " and Gained " + event.getAmount() + " Mobcoins!");
                        } else {
                            p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A " + e.getEntityType().getName() + " and Gained " + event.getAmount() + " Mobcoin!");
                        }
                        CoinsAPI.addCoins(player, event.getAmount());
                    }
                }
            }
        }
    }
}


