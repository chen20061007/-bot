package com.dcbot.listeners;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;

public class modal extends ListenerAdapter {
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Properties prefixCommandData = new Properties();

        if (event.getModalId().equals("send-message")) {
            String msg = event.getValue("info").getAsString();
            event.reply("訊息已傳送").setEphemeral(true).queue();
            event.getMessageChannel().sendMessage(msg).queue();
            System.out.println(event.getMember().getUser() + "匿名傳送了" + msg);
        }
        else if (event.getModalId().equals("addPrefixCommand")) {
            String keyWord = event.getValue("keyWord").getAsString();
            String message = event.getValue("message").getAsString();

            prefixCommandData.setProperty(keyWord, message);
            event.reply("已新增了一個新的關鍵字指令").queue();
            try (InputStream inputStream = new FileInputStream("prefixCommands.properties")){
                prefixCommandData.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (OutputStream output = new FileOutputStream("prefixCommands.properties"))
            {
                prefixCommandData.store(output, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
