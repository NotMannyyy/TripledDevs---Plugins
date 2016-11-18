package dev.mannyyy.bounty.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mannyyy.bounty.BountiesPlugin;
import net.milkbowl.vault.economy.EconomyResponse;

public class mainCommands implements CommandExecutor{
	
	ArrayList<String> confirm = new ArrayList<String>();
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player)commandSender;
        String bountyamount = BountiesPlugin.getPlugin().getConfig().getString("Bounties." +p.getName() + ".bounty");
        if (bountyamount == null) {
            bountyamount = "0";
        }
        if (command.getName().equalsIgnoreCase("bounty")) {
        	
            if (args.length == 0) {
            	p.sendMessage(ChatColor.translateAlternateColorCodes('&', BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("Bounty")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
                return true;
            } if(args.length == 1){
            	if(args[0].equalsIgnoreCase("fix")){
                	if(confirm.contains(p.getName())){
            		for(String key : BountiesPlugin.getPlugin().getConfig().getKeys(false)){
            			BountiesPlugin.getPlugin().getConfig().set(key,null);
                		BountiesPlugin.getPlugin().saveConfig();
                		BountiesPlugin.getPlugin().reloadConfig();
                		BountiesPlugin.getPlugin().getConfig().options().copyDefaults(true);
                		BountiesPlugin.getPlugin().saveConfig();
                		BountiesPlugin.getPlugin().reloadConfig();
            		}
            		BountiesPlugin.getPlugin().saveConfig();
            		BountiesPlugin.getPlugin().reloadConfig();
            		BountiesPlugin.getPlugin().getConfig().options().copyDefaults(true);
            		BountiesPlugin.getPlugin().saveConfig();
            		BountiesPlugin.getPlugin().reloadConfig();
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + "&7Config has been fixed").replace("{arrow}", "»").replace("{bamount}", bountyamount));
            		confirm.remove(p.getName());
            		return true;
            	} else {
            		confirm.add(p.getName());
            		Bukkit.getScheduler().scheduleSyncDelayedTask(BountiesPlugin.plugin, new Runnable()
                    {
            			public void run()
                        {
                        	confirm.remove(p.getName());
                        }
                    }, 20 * 10);
            		p.sendMessage(ChatColor.RED + "Warning: Fixing config file will delete all customization and current bounties");
            		p.sendMessage(ChatColor.RED + "If you wish to proceed type /bounty fix again. (10s)");
            		return true;
            	}
            	}
            	if(args[0].equalsIgnoreCase("reload")){
            		BountiesPlugin.getPlugin().reloadConfig();
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + "&7Config Reloaded").replace("{arrow}", "»").replace("{bamount}", bountyamount));
            		return true;
            	}
            	if(args[0].equalsIgnoreCase("license")){
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + "&7Created By Mannyyy\n" + BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " &7https://www.spigotmc.org/members/mannyyy.256969/").replace("{arrow}", "»").replace("{bamount}", bountyamount));
            		return true;
            	}
            	Player target = Bukkit.getServer().getPlayer(args[0]);
        		if(target != null){
        			p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("TargetBounty")).replace("{arrow}", "»").replace("{bamount}", bountyamount).replace("{target}", target.getName()).replace("{tbamount}", "") + BountiesPlugin.getPlugin().getConfig().getInt(target.getName() + ".bounty"));
        			return true;
        		}
                if (args[0].equalsIgnoreCase("add")) {
                	p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("AddBountyHelp")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
                	return true;
            	} else {
                	for(String msg : BountiesPlugin.getPlugin().getConfig().getStringList("HelpCommand")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("{arrow}", "»"));
                    }
                	return true;
            	}
                	
            } if (args.length == 2){
            	if (args[0].equalsIgnoreCase("add")) {
            		p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("AddBountyHelp")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
            		return true;
            	} else {
                	for(String msg : BountiesPlugin.getPlugin().getConfig().getStringList("HelpCommand")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("{arrow}", "»"));
                    }
                	return true;
            	}
            	
            } if(args.length == 3){
            	if(args[0].equalsIgnoreCase("add")){
            		if(args[1].length() > 0) {
                		Player target = Bukkit.getServer().getPlayer(args[1]);
                		if(target != null){
    						int num = Integer.parseInt(args[2]);
    						EconomyResponse r = BountiesPlugin.econ.withdrawPlayer(p.getName(), num);
    						if(!r.transactionSuccess()){
    							p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("NotEnoughtMoney")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
    						} else {
                			try {
                			if(Integer.parseInt(args[2]) <= 0){
                				p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("LowPlace")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
                				return true;
                			} else {
            						BountiesPlugin.getPlugin().getConfig().set(target.getName() + ".bounty", BountiesPlugin.getPlugin().getConfig().getInt(target.getName() + ".bounty") + num);
            						BountiesPlugin.getPlugin().saveConfig();
            						Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("BountyPlaceBroadcast")).replace("{arrow}", "»").replace("{bamount}", bountyamount).replace("{target}", target.getName()).replace("{tbamount}", BountiesPlugin.getPlugin().getConfig().getString(target.getName() + ".bounty")));
            						return true;
            					}
                			} catch (Exception e){
                				p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("InvalidAmount")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
                				return true;
                			}
                		}
                		} else {
                			p.sendMessage(ChatColor.translateAlternateColorCodes('&',BountiesPlugin.getPlugin().getConfig().getString("Prefix") + " " + BountiesPlugin.getPlugin().getConfig().getString("PlayerNotOnline")).replace("{arrow}", "»").replace("{bamount}", bountyamount));
                			return true;
            				}
            			}
            		}
            	} else {
                	for(String msg : BountiesPlugin.getPlugin().getConfig().getStringList("HelpCommand")) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("{arrow}", "»"));
                    }
                	return true;
            	}           
            } else {
            	for(String msg : BountiesPlugin.getPlugin().getConfig().getStringList("HelpCommand")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("{arrow}", "»"));
                }
            	return true;
        }
        return false;
    }
}
