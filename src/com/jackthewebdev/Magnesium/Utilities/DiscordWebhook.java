package com.jackthewebdev.Magnesium.Utilities;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class DiscordWebhook {
    public int sendWebhook(String webhookUrl, String payload) throws NoSuchAlgorithmException, IOException, KeyManagementException {
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

        return status;
    }
}
