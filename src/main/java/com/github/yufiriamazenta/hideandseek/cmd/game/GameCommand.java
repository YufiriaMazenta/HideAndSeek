package com.github.yufiriamazenta.hideandseek.cmd.game;

import crypticlib.command.ISubCmdExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum GameCommand implements ISubCmdExecutor {

    INSTANCE;
    private final Map<String, ISubCmdExecutor> subCommandMap;

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
    public String permission() {
        return "hideandseek.command.game";
    }

    @Override
    public @NotNull Map<String, ISubCmdExecutor> subCommands() {
        return subCommandMap;
    }
}
