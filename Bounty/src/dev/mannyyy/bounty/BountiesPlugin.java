package dev.mannyyy.bounty;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mannyyy.bounty.Listeners.mainListener;
import dev.mannyyy.bounty.commands.mainCommands;
import net.milkbowl.vault.economy.Economy;


public class BountiesPlugin extends JavaPlugin{
	
	public static Economy econ = null;
	public static BountiesPlugin plugin;
	
	public void onEnable(){
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		plugin = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		BountiesPlugin.registerEvents(this, new mainListener());
		this.getCommand("bounty").setExecutor(new mainCommands());
		PluginDescriptionFile pdf = getDescription();
		Bukkit.getServer().getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		Bukkit.getServer().getLogger().info("[Bounty V1] Enabled Version " + pdf.getVersion());
		Bukkit.getServer().getLogger().info("Coded by: Mannyyy");
		Bukkit.getServer().getLogger().info("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
		
	}
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
    public static BountiesPlugin getPlugin() {
        return plugin;
    }
    
    public static void registerEvents(Plugin plugin, Listener ... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

}
