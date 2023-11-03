package com.github.yufiriamazenta.hideandseek;

import crypticlib.BukkitPlugin;
import crypticlib.CrypticLib;
import crypticlib.scheduler.task.ITask;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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
        gameTask = null;
        gameRunnable = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            CrypticLib.platform().teleportPlayer(player, player.getWorld().getSpawnLocation());
            Util.clearPlayerEffect(player);
            player.getInventory().clear();
            player.updateInventory();
            player.setGameMode(GameMode.SPECTATOR);
        }
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hide").unregister();
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("seek").unregister();
    }

    public boolean isGameRunning() {
        return gameTask != null && gameRunnable != null;
    }

    public GameRunnable gameRunnable() {
        return gameRunnable;
    }

    public void setGameRunnable(GameRunnable gameRunnable) {
        this.gameRunnable = gameRunnable;
    }

    public void startGame(GameRunnable gameRunnable) {
        this.gameTask = CrypticLib.platform().scheduler().runTaskTimer(this, gameRunnable, 1, 1);
        this.gameRunnable = gameRunnable;
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

    public static FileConfiguration config() {
        return INSTANCE.getConfig();
    }

}