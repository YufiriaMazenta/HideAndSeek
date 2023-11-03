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
    public String getSubCommandName() {
        return "game";
    }

    @Override
    public String getPerm() {
        return "hideandseek.command.game";
    }

    @Override
    public void setPerm(String s) {}

    @Override
    public @NotNull Map<String, ISubCommand> getSubCommands() {
        return subCommandMap;
    }
}
