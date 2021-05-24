package com.jackthewebdev.Magnesium.Events;

import com.jackthewebdev.Magnesium.Magnesium;
import com.jackthewebdev.Magnesium.Utilities.DiscordWebhook;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class PlayerQuit implements Listener {

    Magnesium plugin;
    private File file;
    final FileConfiguration config;
    private FileConfiguration conf;


    public PlayerQuit(Magnesium instance){
        this.plugin = instance;
        this.config = plugin.getConfig();
        loadConfigFile();
    }



    public void loadConfigFile(){
        file = new File(plugin.getDataFolder(), "src/users.yml");
        conf = YamlConfiguration.loadConfiguration(file);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) throws NoSuchAlgorithmException, IOException, KeyManagementException {
        //FIXME: just doesnt work idk why
        if(config.getBoolean("OnPlayerLeave.Enabled")){
            conf.set("Users." + event.getPlayer().getUniqueId() + ".latestleave", System.currentTimeMillis());
            conf.save(file);
        }


        if(config.getBoolean("Logs.LogUserLeave")){
            if(config.getString("Logs.DiscordWebhook").equalsIgnoreCase("https://www.google.com")){
                plugin.getLogger().info("Could not log user leave event, set the discord webhook url in config.yml");
                return;
            }else{
                DiscordWebhook hook = new DiscordWebhook();
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                String payload = "{\"content\":\""+event.getPlayer().getName()+" has left the game at: "+ formatter.format(date)+"\"}";
                hook.sendWebhook(config.getString("Logs.DiscordWebhook"),payload);
            }
        }else{
            return;
        }
    }



}
