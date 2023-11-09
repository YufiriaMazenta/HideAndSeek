package com.github.yufiriamazenta.hideandseek.cmd.reload;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import com.github.yufiriamazenta.hideandseek.game.GameLifeCycle;
import crypticlib.command.ISubCmdExecutor;
import crypticlib.util.MsgUtil;
import org.bukkit.command.CommandSender;

import java.util.List;

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
    public String permission() {
        return "hideandseek.command.reload";
    }

}
