package com.github.yufiriamazenta.hideandseek.cmd.map;

import crypticlib.command.ISubCmdExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MapCommand implements ISubCmdExecutor {
    INSTANCE;
    private final Map<String, ISubCmdExecutor> subCmdMap;

    MapCommand() {
        subCmdMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, List<String> args) {
        //TODO
        return ISubCmdExecutor.super.onCommand(sender, args);
    }

    @Override
    public String subCommandName() {
        return "map";
    }

    @Override
    public String permission() {
        return "hideandseek.command.map";
    }

    @Override
    public @NotNull Map<String, ISubCmdExecutor> subCommands() {
        return subCmdMap;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, List<String> args) {
        //TODO
        return ISubCmdExecutor.super.onTabComplete(sender, args);
    }
}
