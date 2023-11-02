package com.github.yufiriamazenta.hideandseek.cmd.sub;

import com.github.yufiriamazenta.hideandseek.GameRunnable;
import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.CrypticLib;
import crypticlib.command.ISubCommand;
import crypticlib.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum GameStartCommand implements ISubCommand {

    INSTANCE;

    @Override
    public String getSubCommandName() {
        return "start";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        GameRunnable gameRunnable = HideAndSeek.INSTANCE.gameRunnable();
        if (gameRunnable == null) {
            int maxSeekNum = Bukkit.getOnlinePlayers().size() / 2;
            if (!args.isEmpty()) {
                maxSeekNum = Integer.parseInt(args.get(0));
            }
            HideAndSeek.INSTANCE.startGame(new GameRunnable((Collection<Player>) Bukkit.getOnlinePlayers(), maxSeekNum));
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.success"));
            return true;
        }
        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.game_playing"));
        return true;
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.game.start";
    }

    @Override
    public void setPerm(String s) {}

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return new HashMap<>();
    }

}
