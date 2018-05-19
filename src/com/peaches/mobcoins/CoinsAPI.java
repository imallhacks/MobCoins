 package com.peaches.mobcoins;
 
 class CoinsAPI implements org.bukkit.event.Listener {
   private static Main plugin;
   
   public CoinsAPI(Main pl) {
     plugin = pl;
   }
   
   public static Integer getCoins(String p) {
     if (playerExists(p)) {
       return 0;
     }
     return plugin.coins.get(p);
   }
   
   public static void addCoins(String p, int coins) {
     int c = getCoins(p);
     c += coins;
     plugin.coins.put(p, c);
   }
   
   public static void removeCoins(String p, int coins) {
     int c = getCoins(p);
     c -= coins;
     plugin.coins.put(p, c);
   }
   
   public static void setCoins(String p, int coins) {
     plugin.coins.put(p, coins);
   }
   
   public static void createPlayer(String p) {
     if (playerExists(p)) {
       plugin.coins.put(p, 0);
     }
   }
   
   public static boolean playerExists(String player) {
     return !plugin.coins.containsKey(player);
   }
 }


