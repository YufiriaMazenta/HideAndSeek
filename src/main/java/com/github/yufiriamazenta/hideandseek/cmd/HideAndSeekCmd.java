package com.github.yufiriamazenta.hideandseek.cmd;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import com.github.yufiriamazenta.hideandseek.cmd.game.GameCommand;
import com.github.yufiriamazenta.hideandseek.cmd.reload.ReloadCommand;
import crypticlib.annotations.BukkitCommand;
import crypticlib.command.IPluginCmdExecutor;
import crypticlib.command.ISubCmdExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@BukkitCommand(name = "hideandseek", permission = "hideandseek.command", alias = {"has", "hs"})
public class HideAndSeekCmd implements IPluginCmdExecutor {

    private final Map<String, ISubCmdExecutor> subCommandMap;

    private HideAndSeekCmd() {
        subCommandMap = new ConcurrentHashMap<>();
        regSubCommand(GameCommand.INSTANCE);
        regSubCommand(ReloadCommand.INSTANCE);
    }

    @Override
    public Plugin getPlugin() {
        return HideAndSeek.INSTANCE;
    }

    @Override
    public @NotNull Map<String, ISubCmdExecutor> subCommands() {
        return subCommandMap;
    }

}
