package com.dcbot.commands;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

public class modalSlashCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("匿名訊息-modal")) {
            TextInput input = TextInput.create("info", "訊息內容", TextInputStyle.PARAGRAPH)
                    .setRequired(true)
                    .setMinLength(1)
                    .build();
            Modal modal = Modal.create("send-message", "匿名傳送一段訊息")
                    .addActionRows(ActionRow.of(input))
                    .build();
            event.replyModal(modal).queue();
        }
        else if (event.getName().equals("新增關鍵字指令")) {
            TextInput input1 = TextInput.create("keyWord", "關鍵字", TextInputStyle.SHORT)
                    .setRequired(true)
                    .setMinLength(1)
                    .build();
            TextInput input2 = TextInput.create("message", "傳送的訊息", TextInputStyle.PARAGRAPH)
                    .setRequired(true)
                    .setMinLength(1)
                    .build();
            Modal modal = Modal.create("addPrefixCommand", "新增關鍵字指令")
                    .addActionRows(ActionRow.of(input1))
                    .addActionRows(ActionRow.of(input2))
                    .build();
            event.replyModal(modal).queue();
        }
    }
}
