package com.peaches.mobcoins;


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobCoinsGiveEvent extends Event {

    Player p;
    int amount;

    public MobCoinsGiveEvent(Player p, int amount) {
        this.p = p;
        this.amount = amount;
    }

    public Player getPlayer() {
        return p;
    }

    public Integer getAmount(){
        return amount;
    }

    public void  setAmount(int amount){
        this.amount = amount;
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}