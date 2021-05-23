package com.jackthewebdev.Magnesium.Commands;


import com.jackthewebdev.Magnesium.Magnesium;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class report implements CommandExecutor {

    final Magnesium plugin;

    public report(Magnesium plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            if(plugin.getConfig().getBoolean("Report.Enabled")){
                Player reported = Bukkit.getPlayer(strings[0]);
                StringBuilder sb = new StringBuilder();
                for(int i = 1; i < strings.length; i++){
                    sb.append(strings[i]).append(" ");
                }
                if(reported == null){
                    commandSender.sendMessage("Provide a username");
                    return false;
                }
                String webhookUrl = plugin.getConfig().getString("Report.DiscordWebhook");
                if(webhookUrl.equals("https://www.google.com")){
                    commandSender.sendMessage("The server owner has not configured this command correctly");
                    return false;
                }else{
                    String endStr = commandSender.getName()+" has reported: "+reported.getName()+" for: "+sb.toString();
                    String payload = "{\"content\":\""+endStr+"\",\"username\":\"User report\"}";
                    System.out.println("Payload: "+payload);


                    try {

                        URL url = new URL(webhookUrl);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestProperty("content-type","application/json");
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);

                        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                            writer.write(payload.getBytes(StandardCharsets.UTF_8));

                            // Always flush and close
                            writer.flush();
                            writer.close();
                        }

                        connection.connect();
                        int status = connection.getResponseCode();
                        System.out.println("Response: "+status);



                    } catch (Exception e){
                        e.printStackTrace();
                    }


                    commandSender.sendMessage("Report filed");
                }
                return true;
            }else{
                commandSender.sendMessage("This command has been disabled");
            }
        }
        return false;

    }
}
