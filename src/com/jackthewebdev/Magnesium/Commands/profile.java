package com.jackthewebdev.Magnesium.Commands;

import com.jackthewebdev.Magnesium.Magnesium;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class profile implements CommandExecutor {

    Magnesium plugin;
    final FileConfiguration config;
    private File file;
    private FileConfiguration conf;



    public profile(Magnesium instance){
        this.plugin = instance;
        this.config = plugin.getConfig();
    }


    public void loadConfigFile(){
        file = new File(plugin.getDataFolder(), "src/users.yml");
        conf = YamlConfiguration.loadConfiguration(file);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            if(player.hasPermission("magnesium.viewConfig")){
                //TODO: test
                if(!(strings[0] == null)){
                    commandSender.sendMessage("Please provide a player");
                    return false;
                }
                Player player1 = plugin.getServer().getPlayer(strings[0]);
                if(player1.getPlayer() == null){
                    commandSender.sendMessage("Invalid player name");
                    return  false;
                }
                UUID uuid = player1.getUniqueId();

                if(conf.contains("Users."+uuid.toString())){
                    //TODO: have this convert the timestamp to a human readable format (MM/DD/YYYY)?
                    if(conf.contains("Users."+uuid.toString()+".firstjoin")){
                        int UnixTimestamp = conf.getInt("Users."+uuid.toString()+".firstjoin");
                        commandSender.sendMessage(player1.getName() + " First joined: "+UnixTimestamp);
                    }
                    if(conf.contains("Users."+uuid.toString()+".latestjoin")){
                        int UnixTimestamp = conf.getInt("Users."+uuid.toString()+".latestjoin");
                        commandSender.sendMessage(player1.getName()+" Last joined on: "+UnixTimestamp);
                    }
                }else{
                    commandSender.sendMessage(ChatColor.RED+"No information on this user, are the logs on?"+ChatColor.RESET);
                    return false;
                }

                return true;
            }else{
                player.sendMessage("Missing Permissions");
                return false;
            }
        }
        return false;
    }
}
