package com.peaches.mobcoins;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class Utils implements Listener {
    private static Main plugin;

    public Utils(Main pl) {
        plugin = pl;
    }

    public static int getChange(String Mob) {
        return plugin.getConfig().getInt("DropChances." + Mob);
    }

    public static Boolean hasmob(String Mob) {
        return plugin.getConfig().contains("DropChances." + Mob);
    }

    @NotNull
    private static ItemStack createItem(@NotNull Material mat, int amt, int durability, String name) {
        ItemStack item = new ItemStack(mat, amt);
        ItemMeta meta = item.getItemMeta();
        item.setDurability((short) durability);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static int getPrice(int i, Player p) {
        return plugin.getConfig().getInt("Shop." + i + ".Price");
    }

    @NotNull
    private static ItemStack getinfo() {
        ItemStack item = new ItemStack(Material.BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "MobCoins" + ChatColor.GRAY + " Can be obtained by killing" +
                ChatColor.BOLD + " Hostile" + ChatColor.RESET + ChatColor.GRAY + " mobs.");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + ChatColor.UNDERLINE + "Available Mobs:");
        //TODO
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getitem(int i, Player p){
        int amount = plugin.getConfig().getInt("Shop." + i + ".Amount");
        String name = plugin.getConfig().getString("Shop." + i + ".DisplayName");
        int id = plugin.getConfig().getInt("Shop." + i + ".Meta");
        Material mat = Material.getMaterial(plugin.getConfig().getString("Shop." + i + ".Item").toUpperCase());
        ItemStack item;
        if (mat != null) {
            item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Shop." + i + ".Item").toUpperCase()), amount, (short) id);
        } else {
            item = new ItemStack(Material.STONE, amount, (short) id);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        ArrayList<String> lore = new ArrayList<>();
        if (plugin.getConfig().contains("Shop." + i + ".Lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("Shop." + i + ".Lore")));
        }
        lore.add(ChatColor.GRAY + "Price: " +
                plugin.getConfig().getInt("Shop." + i + ".Price"));


        if (plugin.getConfig().getInt("Players." + p.getUniqueId() + ".Tokens") < plugin.getConfig().getInt("Shop." + i + ".Price"))
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
                ChatColor.BOLD + "" + ChatColor.GOLD + "You Have " + CoinsAPI.getCoins(player) + " MobCoins!");
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

    public static Inventory showInventory(@NotNull Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, getTitle());
        for (int i = 0; i <= 9; i++) {
            inv.setItem(i, getBorder());
        }
        for (int i = 44; i <= 53; i++) {
            inv.setItem(i, getBorder());
        }
        inv.setItem(17, getBorder());
        inv.setItem(18, getBorder());
        inv.setItem(26, getBorder());
        inv.setItem(27, getBorder());
        inv.setItem(35, getBorder());
        inv.setItem(36, getBorder());

        for (String i : plugin.getConfig().getConfigurationSection("Shop").getKeys(false)) {
            int amount = plugin.getConfig().getInt("Shop." + i + ".Amount");
            String name = plugin.getConfig().getString("Shop." + i + ".DisplayName");
            int id = plugin.getConfig().getInt("Shop." + i + ".Meta");
            Material mat = Material.getMaterial(plugin.getConfig().getString("Shop." + i + ".Item").toUpperCase());
            ItemStack item;
            if (mat != null) {
                item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Shop." + i + ".Item").toUpperCase()), amount, (short) id);
            } else {
                item = new ItemStack(Material.STONE, amount, (short) id);
            }
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            ArrayList<String> lore = new ArrayList<>();
            if (plugin.getConfig().contains("Shop." + i + ".Lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("Shop." + i + ".Lore")));
            }
            lore.add(ChatColor.GRAY + "Price: " +
                    plugin.getConfig().getInt("Shop." + i + ".Price"));


            if (plugin.getConfig().getInt("Players." + p.getUniqueId() + ".Tokens") < plugin.getConfig().getInt("Shop." + i + ".Price"))
                meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(plugin.getConfig().getInt("Shop." + i + ".Slot"), item);
        }
        inv.setItem(0, getBorder());
        return inv;
    }
}


