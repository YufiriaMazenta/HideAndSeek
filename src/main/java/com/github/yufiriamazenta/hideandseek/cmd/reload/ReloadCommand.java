package com.github.yufiriamazenta.hideandseek.cmd.reload;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.command.ISubCommand;
import crypticlib.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ReloadCommand implements ISubCommand {

    INSTANCE;

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        HideAndSeek.INSTANCE.reloadConfig();
        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.reload"));
        return ISubCommand.super.onCommand(sender, args);
    }

    @Override
    public String getSubCommandName() {
        return "reload";
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.reload";
    }

    @Override
    public void setPerm(String s) {}

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return new HashMap<>();
    }

}
