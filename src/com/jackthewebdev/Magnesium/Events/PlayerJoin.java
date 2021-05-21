package com.jackthewebdev.Magnesium.Events;
import com.jackthewebdev.Magnesium.Magnesium;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;


public class PlayerJoin implements Listener {
    Magnesium plugin;
    final FileConfiguration config;

    public PlayerJoin(Magnesium instance){
        plugin = instance;
         config = plugin.getConfig();

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(config.getBoolean("OnPlayerJoin.Enabled")) {
            player.sendMessage(config.getString("OnPlayerJoin.Message") + ", " + ChatColor.valueOf(config.getString("OnPlayerJoin.PlayerNameColor")) + player.getName() + ChatColor.WHITE);
        }
        //TODO: check if this is the players first time joining, if so log it in the config file if not just update the latest join
    }
}
