package com.github.yufiriamazenta.hideandseek;

import com.github.yufiriamazenta.hideandseek.game.GameRunnable;
import crypticlib.BukkitPlugin;
import crypticlib.scheduler.task.ITaskWrapper;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HideAndSeek extends BukkitPlugin {

    private Map<UUID, GameRunnable> gameRunnableMap;

    private Map<UUID, ITaskWrapper> gameTaskMap;
    public static HideAndSeek INSTANCE;

    @Override
    public void enable() {
        INSTANCE = this;
        gameTaskMap = new ConcurrentHashMap<>();
        saveDefaultConfig();
        Configs.reloadConfigs();
    }

    @Override
    public void disable() {
        gameRunnableMap.forEach((key, runnable) -> {
            runnable.endGame();
        });
        gameRunnableMap.clear();
        gameTaskMap.forEach((key, task) -> {
            task.cancel();
        });
    }

//    public void endGame() {
//        gameTask.cancel();
//        resetGame();
//    }

    private void resetGame() {
//        gameRunnable.hidePlayerDisguiseMap().forEach((key, val) -> {
//            val.removeDisguise();
//        });
//        gameRunnable.hidePlayerDisguiseMap().clear();
//
//        gameRunnable.timeBossBar().removeAll();
//        Bukkit.removeBossBar(new NamespacedKey(HideAndSeek.INSTANCE, "time"));
//
//        gameRunnable = null;
//        gameTask = null;
//        for (Player player : Bukkit.getOnlinePlayers()) {
//            CrypticLib.platform().teleportEntity(player, player.getWorld().getSpawnLocation());
//            Util.clearPlayerEffect(player);
//            player.getInventory().clear();
//            player.updateInventory();
//            player.setGameMode(GameMode.SPECTATOR);
//        }
//        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hide").unregister();
//        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("seek").unregister();
    }
//
//    public void setGameRunnable(GameRunnable gameRunnable) {
//        this.gameRunnable = gameRunnable;
//    }

    public void startGame(GameRunnable gameRunnable) {
//        this.gameTask = CrypticLib.platform().scheduler().runTaskTimer(this, gameRunnable, 1, 1);
//        this.gameRunnable = gameRunnable;
    }
//
//    public ITaskWrapper gameTask() {
//        return gameTask;
//    }

    public static FileConfiguration config() {
        return INSTANCE.getConfig();
    }

    public Map<UUID, ITaskWrapper> gameTasks() {
        return gameTaskMap;
    }
}