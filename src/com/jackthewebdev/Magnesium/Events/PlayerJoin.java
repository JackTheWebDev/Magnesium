package com.jackthewebdev.Magnesium.Events;
import com.jackthewebdev.Magnesium.Magnesium;
import com.jackthewebdev.Magnesium.Utilities.DiscordWebhook;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


public class PlayerJoin implements Listener {
    Magnesium plugin;

    final FileConfiguration config;

    private File file;
    private FileConfiguration conf;

    public PlayerJoin(Magnesium instance) {
        plugin = instance;
        config = plugin.getConfig();
        loadConfigFile();
    }

    public void loadConfigFile(){
        file = new File(plugin.getDataFolder(), "src/users.yml");
        conf = YamlConfiguration.loadConfiguration(file);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        Player player = event.getPlayer();
        if(config.getBoolean("OnPlayerJoin.Enabled")) {
            player.sendMessage(config.getString("OnPlayerJoin.Message") + ", " + ChatColor.valueOf(config.getString("OnPlayerJoin.PlayerNameColor")) + player.getName() + ChatColor.WHITE);
        }
        if(!conf.contains("Users."+player.getUniqueId()+".firstjoin")) {
                conf.set("Users." + player.getUniqueId() + ".firstjoin", System.currentTimeMillis());
        }
        conf.set("Users."+player.getUniqueId()+".latestjoin",System.currentTimeMillis());
        conf.save(file);


        if(config.getBoolean("Logs.LogUserJoin")){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            String payload = "{\"content\":\""+event.getPlayer().getName()+" has joined the game at: "+ formatter.format(date)+"\"}";
            DiscordWebhook hook = new DiscordWebhook();
            hook.sendWebhook(config.getString("Logs.DiscordWebhook"),payload);
        }
    }
}
