package com.github.yufiriamazenta.hideandseek.cmd.game;

import crypticlib.command.ISubCmdExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public enum GameStartCommand implements ISubCmdExecutor {

    INSTANCE;

    @Override
    public String subCommandName() {
        return "start";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
//        if (HideAndSeek.INSTANCE.isGameRunning()) {
//            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.game_playing"));
//            return true;
//        }
//        int maxSeekNum = 1;
//        if (!args.isEmpty()) {
//            maxSeekNum = Integer.parseInt(args.get(0));
//        }
//        if (Bukkit.getOnlinePlayers().size() < maxSeekNum + 1) {
//            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.not_enough_player"));
//            return true;
//        }
//        //TODO
////        GameRunnable gameRunnable = new GameRunnable((Collection<Player>) Bukkit.getOnlinePlayers(), maxSeekNum);
////        HideAndSeek.INSTANCE.startGame(gameRunnable);
//        MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.start.success"));
        return true;
    }

    @Override
    public String permission() {
        return "hideandseek.command.game.start";
    }

}
