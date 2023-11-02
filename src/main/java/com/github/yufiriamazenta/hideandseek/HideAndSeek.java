package com.github.yufiriamazenta.hideandseek;

import crypticlib.BukkitPlugin;
import crypticlib.CrypticLib;
import crypticlib.scheduler.task.ITask;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class HideAndSeek extends BukkitPlugin {

    private GameRunnable gameRunnable = null;
    private ITask gameTask = null;
    public static HideAndSeek INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;
        saveDefaultConfig();
    }

    @Override
    public void disable() {

    }

    public void endGame() {
        gameTask.cancel();
        resetGame();
    }

    private void resetGame() {
        //TODO
    }

    public GameRunnable gameRunnable() {
        return gameRunnable;
    }

    public void setGameRunnable(GameRunnable gameRunnable) {
        this.gameRunnable = gameRunnable;
    }

    public void startGame(GameRunnable gameRunnable) {
        this.gameTask = CrypticLib.platform().scheduler().runTaskTimer(this, gameRunnable, 1, 1);
    }

    @NotNull
    @Override
    public FileConfiguration getConfig() {
        reloadConfig();
        return super.getConfig();
    }

    public ITask gameTask() {
        return gameTask;
    }

    public void setGameTask(ITask gameTask) {
        this.gameTask = gameTask;
    }

    public static FileConfiguration config() {
        return INSTANCE.getConfig();
    }

}