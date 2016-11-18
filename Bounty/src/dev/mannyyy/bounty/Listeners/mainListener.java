package dev.mannyyy.bounty.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import dev.mannyyy.bounty.BountiesPlugin;

public class mainListener implements Listener{
	
	@EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            Player k = e.getEntity().getKiller();
            Player p = (Player) e.getEntity();
            String bountyamount = BountiesPlugin.getPlugin().getConfig().getString("Bounties." + p.getName() + ".bounty");
            if (bountyamount == null) {
                bountyamount = "0";
            }
            if(BountiesPlugin.getPlugin().getConfig().getInt(p.getName() + ".bounty") > 0){
            	Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("BountyClaimedBroadcast")).replace("{arrow}", "»").replace("{bamount}", bountyamount).replace("{target}", p.getName()).replace("{tbamount}", BountiesPlugin.getPlugin().getConfig().getString(p.getName() + ".bounty").replace("{player}", k.getName())));
            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + k.getName() + " " + BountiesPlugin.getPlugin().getConfig().getInt(p.getName() + ".bounty"));
            	BountiesPlugin.getPlugin().getConfig().set(p.getName() + ".bounty", 0);
            	BountiesPlugin.getPlugin().saveConfig();
            } else {
            	return;
            }
        }
	}

}
