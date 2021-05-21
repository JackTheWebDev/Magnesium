package com.jackthewebdev.Magnesium;


import com.jackthewebdev.Magnesium.Commands.Test;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Magnesium extends JavaPlugin {

    @Override
    public void onEnable(){
        getLogger().info("Magnesium Enabled!");
        this.getCommand("test").setExecutor(new Test());
    }

    @Override
    public void onDisable(){
        getLogger().info("Magnesium Disabled");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
