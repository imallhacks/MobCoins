package com.peaches.mobcoins;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Main extends org.bukkit.plugin.java.JavaPlugin implements Listener {
    final HashMap<String, Integer> coins = new HashMap<>();

    public Main(Main pl) {
    }

    public Main() {
        PluginDescriptionFile pdf = getDescription();
    }
    //Shit to do
    //Fix Bugs
    //Fix Cringy code
    // Withdraw Command
    // Placeholder API Support?
    // Add other ways e.g. Mining killing Players ect

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getCommand("mobcoins").setExecutor(new Commands(this));
        registerEvents();
        createFile();
        createMessages();
        loadBal();
        //Try to find an update
        try {
            URL checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=38676");
            URLConnection con = checkURL.openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
            if (!getDescription().getVersion().equals(newVersion)) {
                System.out.print("An update was found! New version: " + newVersion
                        + " download: https://www.spigotmc.org/resources/38676/");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //Plugin info
        Metrics metrics = new Metrics(this);
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("MobCoins Enabled!");
        System.out.print("");
        System.out.print("-------------------------------");
    }

    public void onDisable() {
        System.out.print("-------------------------------");
        System.out.print("");
        System.out.print("MobCoins Disabled!");
        System.out.print("");
        System.out.print("-------------------------------");
        saveBal();
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {

        final Player p = e.getPlayer();
        if (p.isOp()) {
            try {
                URL checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=38676");
                URLConnection con = checkURL.openConnection();
                String newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
                if (!getDescription().getVersion().equals(newVersion)) {
                    p.sendMessage(Utils.getprefix() + ChatColor.GRAY + "An update was found! New version: " + newVersion
                            + " download: https://www.spigotmc.org/resources/38676/");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        if (!CoinsAPI.playerExists(p.getName())) {
            CoinsAPI.createPlayer(p.getName());
        }
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new Utils(this), this);
        pm.registerEvents(new Events(this), this);
        pm.registerEvents(new CoinsAPI(this), this);
    }

    private void createFile() {
        File file = new File("plugins//Mobcoins//Balances.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
            }
        }
    }

    private void createMessages() {

        File msg = new File("plugins//MobCoins//Messages.yml");
        YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);
        if (!msg.exists()) {
            try {
                msg.createNewFile();
            } catch (IOException ignored) {
            }
        }
        if (messages.getString("StartReload") == null) {
            messages.set("StartReload", "&7 Reloading Plugin...");
        }
        if (messages.getString("EndReload") == null) {
            messages.set("EndReload", "&7 Reload Complete took %ms%ms.");
        }
        if (messages.getString("NoPermission") == null) {
            messages.set("NoPermission", "&7 Insufficient Permission!");
        }
        if (messages.getString("Update") == null) {
            messages.set("Update", "&7 A new update was found! Version: %version%!");
        }
        if (messages.getString("Amount") == null) {
            messages.set("Amount", "&7 You have %amount% Mobcoins!");
        }
        if (messages.getString("PlayerAmount") == null) {
            messages.set("PlayerAmount", "&7 %player% has %amount% Mobcoins!");
        }
        if (messages.getString("WholeNumber") == null) {
            messages.set("WholeNumber", "&7 please enter a whole number.");
        }
        if (messages.getString("InvalidPlayer") == null) {
            messages.set("InvalidPlayer", "&7 That player does not exist");
        }
        if (messages.getString("CantPayYourself") == null) {
            messages.set("CantPayYourself", "&7 You can't pay yourself");
        }
        if (messages.getString("PaidPlayer") == null) {
            messages.set("PaidPlayer", "&7 You paid %player% %amount% MobCoins");
        }
        if (messages.getString("YouWerePaid") == null) {
            messages.set("YouWerePaid", "&7 You were paid %amount% MobCoins by %player%");
        }
        if (messages.getString("NoMobCoins") == null) {
            messages.set("NoMobCoins", "&7 You do not have enough MobCoins");
        }
        try {
            messages.save(msg);
        } catch (IOException ignored) {
        }
    }

    private void saveBal() {
        File file = new File("plugins//Mobcoins//Balances.yml");
        YamlConfiguration bal = YamlConfiguration.loadConfiguration(file);
        for (String UUID : this.coins.keySet()) {
            bal.set(UUID, this.coins.get(UUID));
        }
        try {
            bal.save(file);
        } catch (IOException ignored) {
        }
    }

    private void loadBal() {
        File file = new File("plugins//Mobcoins//Balances.yml");
        YamlConfiguration bal = YamlConfiguration.loadConfiguration(file);
        for (String UUID : bal.getKeys(false)) {
            this.coins.put(UUID, bal.getInt(UUID));
        }
    }
}
