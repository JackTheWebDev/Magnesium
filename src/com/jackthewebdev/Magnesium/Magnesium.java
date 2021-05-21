package com.jackthewebdev.Magnesium;


import com.jackthewebdev.Magnesium.Commands.Test;
import com.jackthewebdev.Magnesium.Events.PlayerJoin;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Magnesium extends JavaPlugin {

    @Override
    public void onEnable(){
        Plugin plugin = this;
        getLogger().info("Magnesium Enabled!");
        this.getCommand("test").setExecutor(new Test());
        loadConfig();
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), plugin);
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("Database.SQLUsername","Admin");
        getConfig().addDefault("Database.SQLPassword","password");
        getConfig().addDefault("Database.SQLHost","localhost");
        getConfig().addDefault("OnPlayerJoin.Message","Welcome to the server");
        getConfig().addDefault("OnPlayerJoin.PlayerNameColor", "BLUE");
        saveConfig();
    }

    @Override
    public void onDisable(){
        getLogger().info("Magnesium Disabled");
    }

}
