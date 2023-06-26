package com.dcbot.commands;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
//.setEphemeral(true)是否只給一個人看
public class slashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("hi")) {
            event.reply("hihi").queue();
        }
        else if (event.getName().equals("匿名訊息")) {
            String msg = event.getOption("訊息內容").getAsString();
            event.getChannel().sendMessage(msg).queue();
            event.reply("已傳送訊息").setEphemeral(true).queue();
            System.out.println(event.getMember().getUser() + "匿名傳送了" + msg);
        }
    }



    @Override
    public void onReady(@NotNull ReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        commandData.add(Commands.slash("hi","say hi to you"));

        OptionData opt1 = new OptionData(OptionType.STRING, "訊息內容", "要他傳的訊息內容", true);
        commandData.add(Commands.slash("匿名訊息","讓他傳一段訊息").addOptions(opt1));

        commandData.add(Commands.slash("匿名訊息-modal","讓他傳一段訊息"));

        commandData.add(Commands.slash("新增關鍵字指令", "新增一個自訂的關鍵字指令"));

        event.getJDA().updateCommands().addCommands(commandData).queue();
    }
}
