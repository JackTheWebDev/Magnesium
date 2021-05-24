package com.jackthewebdev.Magnesium.Commands;


import com.jackthewebdev.Magnesium.Magnesium;
import io.netty.handler.codec.http.HttpMethod;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import sun.net.www.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
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
                        System.out.println("webhookUrl: "+webhookUrl);

                        SSLContext sc = SSLContext.getInstance("TLSv1.2");
                        // Init the SSLContext with a TrustManager[] and SecureRandom()
                        sc.init(null, null, new java.security.SecureRandom());

                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                        connection.setRequestProperty("Content-Type","application/json");
                        connection.setRequestProperty("User-Agent","Mozilla");
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setSSLSocketFactory(sc.getSocketFactory());

                        DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
                        writer.write(payload.getBytes(StandardCharsets.UTF_8));

                        // Always flush and close
                        writer.flush();
                        writer.close();

                        connection.getInputStream().close();
                        int status = connection.getResponseCode();
                        connection.disconnect();

                        //TODO: Make sure that the connection actually disconnected, cause this code doesnt run and idk why

                        System.out.println("Response: "+status);
                        System.out.println("Response msg: " + connection.getResponseMessage());



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
