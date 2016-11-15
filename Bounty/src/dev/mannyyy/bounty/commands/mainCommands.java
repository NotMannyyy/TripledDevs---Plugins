package dev.mannyyy.bounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mannyyy.bounty.BountiesPlugin;
import net.milkbowl.vault.economy.EconomyResponse;

public class mainCommands implements CommandExecutor{
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player)commandSender;
        String bountyamount = BountiesPlugin.getPlugin().getConfig().getString(p.getName() + ".bounty");
        if (bountyamount == null) {
            bountyamount = "0";
        }
        if (command.getName().equalsIgnoreCase("bounty")) {
        	
            if (args.length == 0) {
                p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY +  "Current bounty: " + ChatColor.GREEN + "$" + bountyamount);
                return true;
            } if(args.length == 1){
            	if(args[0].equalsIgnoreCase("license")){
            		p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "Created by Mannyyy");
            		return true;
            	}
            	Player target = Bukkit.getServer().getPlayer(args[0]);
        		if(target != null){
        			p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + target.getName() + "'s bounty is: " + ChatColor.GREEN + "$" + BountiesPlugin.getPlugin().getConfig().getInt(target.getName() + ".bounty"));
        			return true;
        		}
                if (args[0].equalsIgnoreCase("add")) {
                	p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "Usage: /bounty add <player> <amount>!");
                	return true;
            	} else {
            		p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------" + ChatColor.RESET + ChatColor.GREEN + " Bounties " + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------");
                	p.sendMessage(ChatColor.GREEN + "/bounty »" + ChatColor.GRAY + " View your bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty <player> »" + ChatColor.GRAY + " View player's bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty add <player> <amount> »" + ChatColor.GRAY + " Place a bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty license »" + ChatColor.GRAY + " Shows license info");
                	return true;
            	}
                	
            } if (args.length == 2){
            	if (args[0].equalsIgnoreCase("add")) {
            		p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "Usage: /bounty add <player> <amount>!");
            		return true;
            	} else {
            		p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------" + ChatColor.RESET + ChatColor.GREEN + " Bounties " + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------");
                	p.sendMessage(ChatColor.GREEN + "/bounty »" + ChatColor.GRAY + " View your bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty <player> »" + ChatColor.GRAY + " View player's bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty add <player> <amount> »" + ChatColor.GRAY + " Place a bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty license »" + ChatColor.GRAY + " Shows license info");
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
    							p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "You don't have enough money!");
    						} else {
                			try {
                			if(Integer.parseInt(args[2]) <= 0){
                				p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "Please enter an amount over 0");
                				return true;
                			} else {
            						BountiesPlugin.getPlugin().getConfig().set(target.getName() + ".bounty", BountiesPlugin.getPlugin().getConfig().getInt(target.getName() + ".bounty") + num);
            						BountiesPlugin.getPlugin().saveConfig();
            						Bukkit.getServer().broadcastMessage(BountiesPlugin.prefix + ChatColor.GRAY + "There is now a $" + BountiesPlugin.getPlugin().getConfig().getInt(target.getName() + ".bounty") + " bounty on " + target.getName());
            						return true;
            					}
                			} catch (Exception e){
                				p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "Please enter a valid amount!");
                				return true;
                			}
                		}
                		} else {
                			p.sendMessage(BountiesPlugin.prefix + ChatColor.GRAY + "That player is not online!");
                			return true;
            				}
            			}
            		}
            	} else {
            		p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------" + ChatColor.RESET + ChatColor.GREEN + " Bounties " + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------");
                	p.sendMessage(ChatColor.GREEN + "/bounty »" + ChatColor.GRAY + " View your bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty <player> »" + ChatColor.GRAY + " View player's bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty add <player> <amount> »" + ChatColor.GRAY + " Place a bounty");
                	p.sendMessage(ChatColor.GREEN + "/bounty license »" + ChatColor.GRAY + " Shows license info");
                	return true;
            	}           
            } else {
            	p.sendMessage("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------" + ChatColor.RESET + ChatColor.GREEN + " Bounties " + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "----------");
            	p.sendMessage(ChatColor.GREEN + "/bounty »" + ChatColor.GRAY + " View your bounty");
            	p.sendMessage(ChatColor.GREEN + "/bounty <player> »" + ChatColor.GRAY + " View player's bounty");
            	p.sendMessage(ChatColor.GREEN + "/bounty add <player> <amount> »" + ChatColor.GRAY + " Place a bounty");
            	p.sendMessage(ChatColor.GREEN + "/bounty license »" + ChatColor.GRAY + " Shows license info");
            	return true;
        }
        return false;
    }
}
