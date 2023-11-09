package com.github.yufiriamazenta.hideandseek.cmd.game;

import crypticlib.command.ISubCmdExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public enum GameEndCommand implements ISubCmdExecutor {

    INSTANCE;

    @Override
    public String subCommandName() {
        return "end";
    }

    @Override
    public String permission() {
        return "hideandseek.command.game.end";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
//        if (!HideAndSeek.INSTANCE.isGameRunning()) {
//            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.end.game_not_exist"));
//            return true;
//        }
//        HideAndSeek.INSTANCE.endGame();
//        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.end.success"));
        return true;
    }

}
