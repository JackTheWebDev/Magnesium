package com.jackthewebdev.Magnesium;


import com.jackthewebdev.Magnesium.Commands.reloadconfig;
import com.jackthewebdev.Magnesium.Commands.test;
import com.jackthewebdev.Magnesium.Commands.report;
import com.jackthewebdev.Magnesium.Events.PlayerJoin;
import com.jackthewebdev.Magnesium.Events.PlayerQuit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Magnesium extends JavaPlugin {

    Plugin plugin = this;

    @Override
    public void onEnable(){
        loadCommands();
        loadConfig();
        RegisterPermissions();
        loadEvents();
        getLogger().info("Magnesium Enabled!");
    }

    public void loadEvents(){
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), plugin);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this),plugin);
    }

    public void loadCommands(){
        this.getCommand("test").setExecutor(new test());
        this.getCommand("report").setExecutor(new report(this));
        this.getCommand("mg reload").setExecutor(new reloadconfig(this));
    }
    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("Database.SQLUsername","Admin");
        getConfig().addDefault("Database.SQLPassword","password");
        getConfig().addDefault("Database.SQLHost","localhost");
        getConfig().addDefault("OnPlayerJoin.Message","Welcome to the server");
        getConfig().addDefault("OnPlayerJoin.PlayerNameColor", "BLUE");
        getConfig().addDefault("OnPlayerJoin.Enabled",false);
        getConfig().addDefault("OnPlayerLeave.Enabled",false);
        getConfig().addDefault("Report.Enabled",true);
        getConfig().addDefault("Report.DiscordWebhook","https://www.google.com");
        getConfig().addDefault("Logs.LogUserJoin",true);
        getConfig().addDefault("Logs.LogUserLeave",true);
        getConfig().addDefault("Logs.DiscordWebhook","https://www.google.com");
        saveConfig();
    }

    public void RegisterPermissions(){
        PluginManager pm = this.getServer().getPluginManager();
        pm.addPermission(new Permission("magnesium.viewConfig"));
    }

    @Override
    public void onDisable(){
        getLogger().info("Magnesium Disabled");
    }

}
