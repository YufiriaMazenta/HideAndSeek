package com.github.yufiriamazenta.hideandseek;

import com.github.yufiriamazenta.hideandseek.game.GameRunnable;
import crypticlib.BukkitPlugin;
import crypticlib.CrypticLib;
import crypticlib.scheduler.task.ITaskWrapper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class HideAndSeek extends BukkitPlugin {

    private GameRunnable gameRunnable = null;
    private ITaskWrapper gameTask = null;
    public static HideAndSeek INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;
        saveDefaultConfig();
    }

    @Override
    public void disable() {
        Team hide = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hide");
        if (hide != null)
            hide.unregister();
        Team seek = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("seek");
        if (seek != null)
            seek.unregister();
        Bukkit.removeBossBar(new NamespacedKey(HideAndSeek.INSTANCE, "time"));
    }

    public void endGame() {
        gameTask.cancel();
        resetGame();
    }

    private void resetGame() {
        gameRunnable.hidePlayerDisguiseMap().forEach((key, val) -> {
            val.removeDisguise();
        });
        gameRunnable.hidePlayerDisguiseMap().clear();

        gameRunnable.timeBossBar().removeAll();
        Bukkit.removeBossBar(new NamespacedKey(HideAndSeek.INSTANCE, "time"));

        gameRunnable = null;
        gameTask = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            CrypticLib.platform().teleportEntity(player, player.getWorld().getSpawnLocation());
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

    public ITaskWrapper gameTask() {
        return gameTask;
    }

    public static FileConfiguration config() {
        return INSTANCE.getConfig();
    }

}