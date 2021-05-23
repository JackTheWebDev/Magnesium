package com.jackthewebdev.Magnesium.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class profile implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            //TODO: Finish this
            Player player = ((Player) commandSender).getPlayer();
            if(player.hasPermission("magnesium.viewConfig")){
                if(!(strings[0] == null)){

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
