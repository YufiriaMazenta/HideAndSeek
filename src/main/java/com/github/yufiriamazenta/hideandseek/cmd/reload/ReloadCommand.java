package com.github.yufiriamazenta.hideandseek.cmd.reload;

import com.github.yufiriamazenta.hideandseek.game.GameLifeCycle;
import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.command.ISubCmdExecutor;
import crypticlib.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ReloadCommand implements ISubCmdExecutor {

    INSTANCE;

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        HideAndSeek.INSTANCE.reloadConfig();
        GameLifeCycle.resetMaxSecond();
        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.reload"));
        return true;
    }

    @Override
    public String subCommandName() {
        return "reload";
    }

    @Override
    public String perm() {
        return "hideandseek.command.reload";
    }

    @Override
    public @NotNull Map<String, ISubCmdExecutor> subCommands() {
        return new HashMap<>();
    }

}
