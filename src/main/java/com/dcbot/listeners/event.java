package com.dcbot.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

public class event extends ListenerAdapter {
    Properties prefixCommandData = new Properties();
    Properties config = new Properties();
    static String prefix;//前綴
    Set<String> keys;//所有的關鍵字
    public void getPrefix() {
        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            config.load(fileInputStream);
            prefix = config.getProperty("PREFIX");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        try (InputStream input = new FileInputStream("prefixCommands.properties");
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8))
        {
            prefixCommandData.load(reader);
            keys = prefixCommandData.stringPropertyNames();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = event.getMessage().getContentRaw();
        String[] messageArray = message.split(" ");
        if (message.startsWith(prefix)) {
            for (String key : keys) {
                if (message.contains(key)) {
                    event.getChannel().sendMessage(prefixCommandData.getProperty(key)).queue();
                    break;
                }
            }
        }
    }
}
