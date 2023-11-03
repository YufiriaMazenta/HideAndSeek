package com.github.yufiriamazenta.hideandseek.cmd.disguises;

import com.github.yufiriamazenta.hideandseek.DisguisesHooker;
import crypticlib.command.ISubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DisguisesCommand implements ISubCommand {

    INSTANCE;

    @Override
    public String getSubCommandName() {
        return "disguises";
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.disguises";
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player player))
            return true;
        if (args.isEmpty())
            return true;
        DisguisesHooker.disguises(player, args.get(0));
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
