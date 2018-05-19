 package com.peaches.mobcoins;
 
 import java.io.File;
import java.util.Random;
 import net.md_5.bungee.api.ChatColor;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.entity.EntityDeathEvent;
 import org.bukkit.event.inventory.InventoryClickEvent;
 import org.bukkit.event.player.PlayerJoinEvent;
 import org.jetbrains.annotations.NotNull;

 class Events implements org.bukkit.event.Listener
 {
   private static Main plugin;
   private final File CE = new File("plugins//CustomEnchants//config.yml");
	private final YamlConfiguration CEConfig = YamlConfiguration.loadConfiguration(CE);
   
   public Events(Main pl)
   {
     plugin = pl;
   }


	private static String convertPower(int i) {
		if (i <= 0) {
			return "I";
		}
		if (i == 1) {
			return "I";
		}
		if (i == 2) {
			return "II";
		}
		if (i == 3) {
			return "III";
		}
		if (i == 4) {
			return "IV";
		}
		if (i == 5) {
			return "V";
		}
		if (i == 6) {
			return "VI";
		}
		if (i == 7) {
			return "VII";
		}
		if (i == 8) {
			return "VIII";
		}
		if (i == 9) {
			return "IX";
		}
		if (i == 10) {
			return "X";
		}
		return i + "";
	}
   @EventHandler
   public void OnPlayerJoin(@NotNull PlayerJoinEvent e) {
     Player p = e.getPlayer();
     String player = p.getUniqueId().toString();
     CoinsAPI.createPlayer(player);
   }
   
   @EventHandler
   public void OnInvClick(@NotNull InventoryClickEvent e) {
     if (e.getInventory().getTitle().equals(Utils.getTitle())) {
       Player p = (Player)e.getWhoClicked();
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
   
   @EventHandler
   public void OnEntityDeath(@NotNull EntityDeathEvent e) {
     if (e.getEntityType().equals(EntityType.SKELETON)) {
       Random object = new Random();
       
 
       for (int counter = 1; counter <= 1; counter++) {
         int i = 1 + object.nextInt(100);
         if (i <= Utils.getskeleton()) {
           Player p = e.getEntity().getKiller();
           if (p == null) return;
           String player = p.getUniqueId().toString();
           if(p.getItemInHand() != null) {
        	   if(p.getItemInHand().hasItemMeta()) {
        		   if(p.getItemInHand().getItemMeta().hasLore()) {
        			   for(int counter1 = 1; counter1 <= CEConfig.getInt("MaxPower.Coins"); counter1++) {
        				   if(Utils.hasenchant("Coins "+convertPower(counter1), p.getItemInHand())) {
        			           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Skeleton and Gained "+(counter1+1)+" Mobcoins!");
        			           CoinsAPI.addCoins(player, counter1+1);
        			           return;
        				   }
        			   }
        		   }
        	   }
           }
           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Skeleton and Gained 1 Mobcoin!");
           CoinsAPI.addCoins(player, 1);
         }
       }
     }
     if (e.getEntityType().equals(EntityType.ZOMBIE)) {
       Random object = new Random();
       
 
       for (int counter = 1; counter <= 1; counter++) {
         int i = 1 + object.nextInt(100);
         if (i <= Utils.getzombie()) {
           Player p = e.getEntity().getKiller();
           if (p == null) return;
           String player = p.getUniqueId().toString();
           if(p.getItemInHand() != null) {
        	   if(p.getItemInHand().hasItemMeta()) {
        		   if(p.getItemInHand().getItemMeta().hasLore()) {
        			   for(int counter1 = 1; counter1 <= CEConfig.getInt("MaxPower.Coins"); counter1++) {
        				   if(Utils.hasenchant("Coins "+convertPower(counter1), p.getItemInHand())) {
        			           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Zombie and Gained "+(counter1+1)+" Mobcoins!");
        			           CoinsAPI.addCoins(player, counter1+1);
        			           return;
        				   }
        			   }
        		   }
        	   }
           }
           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Zombie and Gained 1 Mobcoin!");
           CoinsAPI.addCoins(player, 1);
         }
       }
     }
     if (e.getEntityType().equals(EntityType.BLAZE)) {
       Random object = new Random();
       
 
       for (int counter = 1; counter <= 1; counter++) {
         int i = 1 + object.nextInt(100);
         if (i <= Utils.getblaze()) {
           Player p = e.getEntity().getKiller();
           if (p == null) return;
           String player = p.getUniqueId().toString();
           if(p.getItemInHand() != null) {
        	   if(p.getItemInHand().hasItemMeta()) {
        		   if(p.getItemInHand().getItemMeta().hasLore()) {
        			   for(int counter1 = 1; counter1 <= CEConfig.getInt("MaxPower.Coins"); counter1++) {
        				   if(Utils.hasenchant("Coins "+convertPower(counter1), p.getItemInHand())) {
        			           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Blaze and Gained "+(counter1+1)+" Mobcoins!");
        			           CoinsAPI.addCoins(player, counter1+1);
        			           return;
        				   }
        			   }
        		   }
        	   }
           }
           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Blaze and Gained 1 Mobcoin!");
           CoinsAPI.addCoins(player, 1);
         }
       }
     }
     if (e.getEntityType().equals(EntityType.ENDERMAN)) {
       Random object = new Random();
       
 
       for (int counter = 1; counter <= 1; counter++) {
         int i = 1 + object.nextInt(100);
         if (i <= Utils.getenderman()) {
           Player p = e.getEntity().getKiller();
           if (p == null) return;
           String player = p.getUniqueId().toString();
           if(p.getItemInHand() != null) {
        	   if(p.getItemInHand().hasItemMeta()) {
        		   if(p.getItemInHand().getItemMeta().hasLore()) {
        			   for(int counter1 = 1; counter1 <= CEConfig.getInt("MaxPower.Coins"); counter1++) {
        				   if(Utils.hasenchant("Coins "+convertPower(counter1), p.getItemInHand())) {
        			           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed An Enderman and Gained "+(counter1+1)+" Mobcoins!");
        			           CoinsAPI.addCoins(player, counter1+1);
        			           return;
        				   }
        			   }
        		   }
        	   }
           }
           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed An Enderman and Gained 1 Mobcoin!");
           CoinsAPI.addCoins(player, 1);
         }
       }
     }
     if (e.getEntityType().equals(EntityType.CREEPER)) {
       Random object = new Random();
       
 
       for (int counter = 1; counter <= 1; counter++) {
         int i = 1 + object.nextInt(100);
         if (i <= Utils.getcreeper()) {
           Player p = e.getEntity().getKiller();
           if (p == null) return;
           String player = p.getUniqueId().toString();
           if(p.getItemInHand() != null) {
        	   if(p.getItemInHand().hasItemMeta()) {
        		   if(p.getItemInHand().getItemMeta().hasLore()) {
        			   for(int counter1 = 1; counter1 <= CEConfig.getInt("MaxPower.Coins"); counter1++) {
        				   if(Utils.hasenchant("Coins "+convertPower(counter1), p.getItemInHand())) {
        			           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Creeper and Gained "+(counter1+1)+" Mobcoins!");
        			           CoinsAPI.addCoins(player, counter1+1);
        			           return;
        				   }
        			   }
        		   }
        	   }
           }
           p.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Killed A Creeper and Gained 1 Mobcoin!");
           CoinsAPI.addCoins(player, 1);
         }
       }
     }
   }
 }


