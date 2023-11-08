package com.github.yufiriamazenta.hideandseek.cmd.game;

import com.github.yufiriamazenta.hideandseek.game.GameRunnable;
import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.command.ISubCmdExecutor;
import crypticlib.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GameStartCommand implements ISubCmdExecutor {

    INSTANCE;

    @Override
    public String subCommandName() {
        return "start";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if (HideAndSeek.INSTANCE.isGameRunning()) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.game_playing"));
            return true;
        }
        int maxSeekNum = 1;
        if (!args.isEmpty()) {
            maxSeekNum = Integer.parseInt(args.get(0));
        }
        if (Bukkit.getOnlinePlayers().size() < maxSeekNum + 1) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.not_enough_player"));
            return true;
        }
        //TODO
//        GameRunnable gameRunnable = new GameRunnable((Collection<Player>) Bukkit.getOnlinePlayers(), maxSeekNum);
//        HideAndSeek.INSTANCE.startGame(gameRunnable);
        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.success"));
        return true;
    }

    @Override
    public String permission() {
        return "hideandseek.command.game.start";
    }

    @Override
    public @NotNull Map<String, ISubCmdExecutor> subCommands() {
        return new HashMap<>();
    }

}
