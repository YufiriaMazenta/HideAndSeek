package com.github.yufiriamazenta.hideandseek.cmd.disguises;

import com.github.yufiriamazenta.hideandseek.DisguisesHooker;
import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.command.ISubCommand;
import crypticlib.util.MsgUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public enum DisguiseCommand implements ISubCommand {

    INSTANCE;

    @Override
    public String getSubCommandName() {
        return "disguise";
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.disguise";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if (!HideAndSeek.INSTANCE.isGameRunning()) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.disguise.game_not_start"));
            return true;
        }
        if (!(sender instanceof Player player)) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.disguise.only_player"));
            return true;
        }
        UUID uuid = player.getUniqueId();
        if (HideAndSeek.INSTANCE.gameRunnable().hidePlayerDisguiseMap().containsKey(uuid))
            return true;
        if (args.isEmpty()) {
            MsgUtil.sendMsg(sender, HideAndSeek.config().getString("plugin_message.command.disguise.not_select_disguise"));
            return true;
        }
        HideAndSeek.INSTANCE.gameRunnable().hidePlayerDisguiseMap().put(uuid, DisguisesHooker.disguises(player, args.get(0)));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        return new ArrayList<>(DisguisesHooker.disguiseFuncMap().keySet());
    }

    @Override
    public void setPerm(String s) {}

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return new HashMap<>();
    }

}
