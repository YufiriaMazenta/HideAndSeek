package com.github.yufiriamazenta.hideandseek.cmd.sub;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.command.ISubCommand;
import crypticlib.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GameEndCommand implements ISubCommand {

    INSTANCE;

    @Override
    public String getSubCommandName() {
        return "end";
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.game.end";
    }

    @Override
    public void setPerm(String s) {}

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if (!HideAndSeek.INSTANCE.isGameRunning()) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.end.game_not_exist"));
            return true;
        }
        HideAndSeek.INSTANCE.endGame();
        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.end.success"));
        return true;
    }

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return new HashMap<>();
    }
}
