package com.jackthewebdev.Magnesium.Commands;

import com.jackthewebdev.Magnesium.Magnesium;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class reloadconfig implements CommandExecutor {

    Magnesium plugin;
    final FileConfiguration config;


    public reloadconfig(Magnesium instance){
        this.plugin = instance;
        this.config = instance.getConfig();
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.hasPermission("magnesium.viewConfig")){
            plugin.saveConfig();
        }else{
            commandSender.sendMessage(ChatColor.RED+"You do not have proper permissions to do this");
            return false;
        }

        return false;
    }
}
