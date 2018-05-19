 package com.peaches.mobcoins;
 
 import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
 import org.bukkit.Bukkit;
 import org.bukkit.Material;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Listener;
 import org.bukkit.inventory.Inventory;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.jetbrains.annotations.NotNull;

 class Utils implements Listener
 {
   private static Main plugin;
   
   public Utils(Main pl)
   {
     plugin = pl;
   }
	
	public static Boolean hasenchant(String Enchant, ItemStack item) {
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasLore()) {
				List<String> lore = item.getItemMeta().getLore();
				for(String l : lore) {
					if(ChatColor.stripColor(l).equals(Enchant)) {
						return true;
					}
				}
			}
		}
		return false;
	}
   
   public static int getenderman() {
     return plugin.getConfig().getInt("DropChances.ENDERMAN");
   }
   
   public static int getcreeper() {
     return plugin.getConfig().getInt("DropChances.CREEPER");
   }
   
   public static int getblaze() {
     return plugin.getConfig().getInt("DropChances.BLAZE");
   }
   
   public static int getzombie() {
     return plugin.getConfig().getInt("DropChances.ZOMBIE");
   }
   
   public static int getskeleton() {
     return plugin.getConfig().getInt("DropChances.SKELETON");
   }
   
   private static int getSlot(int i) {
     return plugin.getConfig().getInt("Shop." + i + ".Slot");
   }
   
   @NotNull
   private static ItemStack createItem(@NotNull Material mat, int amt, int durability, String name) {
     ItemStack item = new ItemStack(mat, amt);
     ItemMeta meta = item.getItemMeta();
     item.setDurability((short)durability);
     meta.setDisplayName(name);
     item.setItemMeta(meta);
     return item;
   }
   
   public static int getPrice(int i, Player p) {
     return plugin.getConfig().getInt("Shop." + i + ".Price");
   }
   
   public static ItemStack getItem(int i, @NotNull Player p) {
     if (!plugin.getConfig().contains("Shop." + i)) {
       return null;
     }
     int amnt = plugin.getConfig().getInt("Shop." + i + ".Amount");
     int id = plugin.getConfig().getInt("Shop." + i + ".Meta");
     ItemStack item = new ItemStack(
       Material.getMaterial(plugin.getConfig()
       .getString("Shop." + i + ".Item").toUpperCase()), 
       amnt, (short)id);
     ItemMeta im = item.getItemMeta();
     im.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig()
       .getString("Shop." + i + ".DisplayName")));
     ArrayList<String> lore = new ArrayList<>();
     if (plugin.getConfig().contains("Shop." + i + ".Lore")) {
       lore.add(ChatColor.translateAlternateColorCodes('&', 
         plugin.getConfig().getString("Shop." + i + ".Lore")));
     }
     lore.add(ChatColor.GRAY + "Price: " + 
       plugin.getConfig().getInt("Shop." + i + ".Price"));
     
 
 
     if (plugin.getConfig().getInt("Players." + p.getUniqueId() + ".Tokens") < plugin.getConfig().getInt("Shop." + i + ".Price"))
       im.setLore(lore);
     item.setItemMeta(im);
     return item;
   }
   
   @NotNull
   private static ItemStack getinfo() {
     ItemStack item = new ItemStack(Material.BOOK);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(ChatColor.GOLD + "MobCoins" + ChatColor.GRAY + " Can be obtained by killing" + 
       ChatColor.BOLD + " Hostile" + ChatColor.RESET + ChatColor.GRAY + " mobs.");
     ArrayList<String> lore = new ArrayList<>();
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + ChatColor.UNDERLINE + "Available Mobs:");
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + "Skeleton (" + getskeleton() + "%)");
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + "Zombie (" + getzombie() + "%)");
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + "Blaze (" + getblaze() + "%)");
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + "Enderman (" + getenderman() + "%)");
     lore.add(ChatColor.GRAY +""+ ChatColor.BOLD + "Creeper (" + getcreeper() + "%)");
     meta.setLore(lore);
     item.setItemMeta(meta);
     return item;
   }
   
   @NotNull
   private static ItemStack getCoins(Player p) {
     String player = p.getUniqueId().toString();
     ItemStack item = new ItemStack(Material.DOUBLE_PLANT);
     ItemMeta im = item.getItemMeta();
     im.setDisplayName(
       ChatColor.BOLD +""+ ChatColor.GOLD + "You Have " + CoinsAPI.getCoins(player) + " MobCoins!");
     item.setItemMeta(im);
     return item;
   }
   
   @NotNull
   private static ItemStack getBorder() {
     return createItem(Material.STAINED_GLASS_PANE, 1, 15, " ");
   }
   
   public static String getTitle() {
     return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Title"));
   }
   
   public static String getprefix() {
     return ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Options.Prefix"));
   }
   
   public static boolean hasAccount(Player p) {
     return plugin.getConfig().contains("Players." + p.getUniqueId());
   }
   
   public static Inventory showInventory(@NotNull Player p) {
     Inventory inv = Bukkit.createInventory(null, 54, getTitle());
     inv.setItem(0, getBorder());
     inv.setItem(1, getBorder());
     inv.setItem(2, getBorder());
     inv.setItem(3, getBorder());
     inv.setItem(4, getCoins(p));
     inv.setItem(5, getBorder());
     inv.setItem(6, getBorder());
     inv.setItem(7, getBorder());
     inv.setItem(8, getBorder());
     inv.setItem(9, getBorder());
     inv.setItem(17, getBorder());
     inv.setItem(18, getBorder());
     
     inv.setItem(26, getBorder());
     inv.setItem(27, getBorder());
     inv.setItem(35, getBorder());
     inv.setItem(36, getBorder());
     inv.setItem(44, getBorder());
     inv.setItem(45, getBorder());
     inv.setItem(46, getBorder());
     inv.setItem(47, getBorder());
     inv.setItem(48, getBorder());
     inv.setItem(49, getinfo());
     inv.setItem(50, getBorder());
     inv.setItem(51, getBorder());
     inv.setItem(52, getBorder());
     inv.setItem(53, getBorder());
     for (int i = 0; i < 28; i++) {
       inv.setItem(getSlot(i), getItem(i, p));
     }
     inv.setItem(0, getBorder());
     return inv;
   }
   
   public static boolean fullInv(Player p) {
     int check = p.getInventory().firstEmpty();
     return check == -1;
   }
   
   public static boolean canAfford(Player p, int slot) {
     return plugin.getConfig()
       .getInt("Players." + p.getUniqueId() + ".Tokens") >= plugin
       .getConfig().getInt("Shop." + slot + ".Price");
   }
 }


