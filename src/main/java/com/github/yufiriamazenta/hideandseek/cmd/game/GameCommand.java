package com.github.yufiriamazenta.hideandseek.cmd.game;

import crypticlib.command.ISubCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum GameCommand implements ISubCommand {

    INSTANCE;
    private final Map<String, ISubCommand> subCommandMap;

    GameCommand() {
        subCommandMap = new ConcurrentHashMap<>();
        regSubCommand(GameStartCommand.INSTANCE);
        regSubCommand(GameEndCommand.INSTANCE);
    }

    @Override
    public String subCommandName() {
        return "game";
    }

    @Override
    public String perm() {
        return "hideandseek.command.game";
    }

    @Override
    public @NotNull Map<String, ISubCommand> subCommands() {
        return subCommandMap;
    }
}
