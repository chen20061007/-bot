package com.dcbot;

import com.dcbot.commands.modalSlashCommand;
import com.dcbot.commands.slashCommand;
import com.dcbot.listeners.modal;
import com.dcbot.listeners.event;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class dcbot {

    private final ShardManager shardManager;
    Properties config = new Properties();
    String token, activity;
    public dcbot()throws LoginException {

        try (FileInputStream fileInputStream = new FileInputStream("config.properties")) {
            config.load(fileInputStream);
            activity = config.getProperty("ACTIVITY");
            token = config.getProperty("TOKEN");

        } catch (IOException e) {
            e.printStackTrace();
        }

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        shardManager = builder.build();

        //狀態
        shardManager.setStatus(OnlineStatus.ONLINE);
        shardManager.setActivity(Activity.playing(activity));

        //其他class加入
        shardManager.addEventListener(new event());
        shardManager.addEventListener(new slashCommand());
        shardManager.addEventListener(new modal());
        shardManager.addEventListener(new modalSlashCommand());
    }
    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] arg){
        try {
            dcbot bot = new dcbot();
            System.out.println("Successful Login");
        }
        catch(LoginException e) {
            System.out.println("Login Error!!!!!!!!");
        }
        event event = new event();
        event.getPrefix();
    }
}
