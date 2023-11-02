package com.github.yufiriamazenta.hideandseek.cmd;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import com.github.yufiriamazenta.hideandseek.cmd.sub.GameCommand;
import crypticlib.annotations.BukkitCommand;
import crypticlib.command.IPluginCommand;
import crypticlib.command.ISubCommand;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@BukkitCommand(command = "hideandseek")
public class HideAndSeekCmd implements IPluginCommand {

    private final Map<String, ISubCommand> subCommandMap;

    private HideAndSeekCmd() {
        subCommandMap = new ConcurrentHashMap<>();
        regSubCommand(GameCommand.INSTANCE);
    }

    @Override
    public Plugin getPlugin() {
        return HideAndSeek.INSTANCE;
    }

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return subCommandMap;
    }
}
