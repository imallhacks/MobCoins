package com.peaches.mobcoins;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

class Commands implements CommandExecutor {
    private final Main plugin;
    private final File msg = new File("plugins//MobCoins//Messages.yml");
    private final YamlConfiguration messages = YamlConfiguration.loadConfiguration(msg);

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("mobcoins")) {
            if ((sender instanceof Player)) {
                if (args.length == 0) {
                    Player p = (Player) sender;
                    p.openInventory(Utils.showInventory(p));
                    return true;
                }
                if (args.length == 1) {
                    if ((args[0].equalsIgnoreCase("balance")) || (args[0].equalsIgnoreCase("bal"))) {
                        Player p = (Player) sender;
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("Amount").replace("%amount%", CoinsAPI.getCoins(p.getUniqueId().toString())+"")));
                        return false;
                    }
                    java.text.DecimalFormat localDecimalFormat;
                    if (args[0].equalsIgnoreCase("reload")) {
                        if (!sender.hasPermission("mobcoins.reload")) {
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                            return false;
                        }
                        long l1 = System.currentTimeMillis();
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("StartReload")));
                        plugin.reloadConfig();
                        plugin.getConfig().options().copyDefaults(true);
                        plugin.saveDefaultConfig();
                        plugin.saveConfig();
                        long l2 = System.currentTimeMillis() - l1;
                        localDecimalFormat = new java.text.DecimalFormat("###.##");
                        sender.sendMessage(Utils.getprefix() + ChatColor.translateAlternateColorCodes('&',
                                this.messages.getString("EndReload").replace("%ms%", localDecimalFormat.format(l2))));
                        return false;
                    }
                }

                if ((args.length == 2) && (
                        (args[0].equalsIgnoreCase("balance")) || (args[0].equalsIgnoreCase("bal")))) {
                    Player pl = Bukkit.getServer().getPlayer(args[1]);
                    Player p = (Player) sender;
                    sender.sendMessage(Utils.getprefix()
                            + ChatColor.translateAlternateColorCodes('&', this.messages.getString("PlayerAmount").replace("%amount%", CoinsAPI.getCoins(pl.getName())+"")));
                    return false;
                }

                if (args.length == 3) {

                    if ((args[0].equalsIgnoreCase("give")) || (args[0].equals("add"))) {
                        if (sender.hasPermission("mobcoins.give")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.addCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Gave " + player + " " + amount +
                                    " MobCoins!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("remove")) {
                        if (sender.hasPermission("mobcoins.remove")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.removeCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Took " + amount +
                                    " MobCoins from " + player + "!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("set")) {
                        if (sender.hasPermission("mobcoins.set")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.setCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You set " + player +
                                    "'s MobCoins to " + amount + "!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("pay")) {
                        String player = args[1];
                        Player p = Bukkit.getServer().getPlayer(player);
                        if (p == null) {
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                            return true;
                        }
                        String pl = p.getUniqueId().toString();
                        String cs = sender.getName();
                        Player pcs = Bukkit.getServer().getPlayer(cs);
                        String plcs = pcs.getUniqueId().toString();
                        if (sender.getName().toLowerCase().equals(player.toLowerCase())) {
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("CantPayYourself")));
                            return true;
                        }
                        if (CoinsAPI.playerExists(pl)) {
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                            return true;
                        }
                        int amount;
                        try {
                            amount = Integer.parseInt(args[2]);
                        } catch (Exception e) {
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                            return true;
                        }
                        if(args[2].contains("-")){
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                            return true;
                        }
                        if (CoinsAPI.getCoins(plcs) >= amount) {
                            CoinsAPI.addCoins(pl, amount);
                            CoinsAPI.removeCoins(plcs, amount);
                            sender.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("PaidPlayer").replace("%player%", pl).replace("%amount%", amount+"")));
                            p.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("YouWerePaid").replace("%player%", cs).replace("%amount%", amount+"")));
                        } else {
                            p.sendMessage(Utils.getprefix()
                                    + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoMobCoins")));
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.GRAY + "-------------------[ " +
                            ChatColor.RED+""+ChatColor.BOLD + "MobCoins Help " + ChatColor.GRAY +
                            "]-------------------");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD + "/MobCoins " +
                            ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + "Opens The GUI");

                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD + "/MobCoins balance " +
                            ChatColor.DARK_GRAY + ": " + ChatColor.GRAY +
                            "Tells You You Balance");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD + "/MobCoins balance <Name> " +
                            ChatColor.DARK_GRAY + ": " + ChatColor.GRAY +
                            "Tells You You Balance of another player");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD + "/MobCoins reload " +
                            ChatColor.DARK_GRAY + ": " + ChatColor.GRAY +
                            "Reloads The Plugin");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD +
                            "/MobCoins pay <name> <amount>" + ChatColor.DARK_GRAY + ": " +
                            ChatColor.GRAY + "Pay a player");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD +
                            "/MobCoins add <name> <amount>" + ChatColor.DARK_GRAY + ": " +
                            ChatColor.GRAY + "Give a player MobCoins");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD +
                            "/MobCoins set <name> <amount>" + ChatColor.DARK_GRAY + ": " +
                            ChatColor.GRAY + "Set a players mobcoins");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD +
                            "/MobCoins remove <name> <amount>" + ChatColor.DARK_GRAY + ": " +
                            ChatColor.GRAY + "Remove a players MobCoins");
                    sender.sendMessage(ChatColor.RED+""+ChatColor.BOLD + "/MobCoins help " +
                            ChatColor.DARK_GRAY + ": " + ChatColor.GRAY +
                            "View a helpful list of commands!");
                    sender.sendMessage(ChatColor.GRAY +
                            "-----------------------------------------------------");
                }
            } else {
                if (args.length == 3) {
                    if ((args[0].equalsIgnoreCase("give")) || (args[0].equals("add"))) {
                        if (sender.hasPermission("mobcoins.give")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.addCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Gave " + player + " " + amount +
                                    " MobCoins!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("remove")) {
                        if (sender.hasPermission("mobcoins.remove")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.removeCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You Took " + amount +
                                    " MobCoins from " + player + "!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("set")) {
                        if (sender.hasPermission("mobcoins.set")) {
                            String player = args[1];
                            Player p = Bukkit.getServer().getPlayer(player);
                            String pl = p.getUniqueId().toString();
                            if (CoinsAPI.playerExists(pl)) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("InvalidPlayer")));
                                return true;
                            }
                            int amount;
                            try {
                                amount = Integer.parseInt(args[2]);
                            } catch (Exception e) {
                                sender.sendMessage(Utils.getprefix()
                                        + ChatColor.translateAlternateColorCodes('&', this.messages.getString("WholeNumber")));
                                return true;
                            }
                            CoinsAPI.setCoins(pl, amount);
                            sender.sendMessage(Utils.getprefix() + ChatColor.GRAY + " You set " + player +
                                    "'s MobCoins to " + amount + "!");
                            return true;
                        }
                        sender.sendMessage(Utils.getprefix()
                                + ChatColor.translateAlternateColorCodes('&', this.messages.getString("NoPermission")));
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


