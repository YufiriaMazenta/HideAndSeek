package com.github.yufiriamazenta.hideandseek;

import crypticlib.CrypticLib;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class GameRunnable implements Runnable {

    private GameLifeCycle gameLifeCycle;
    private final List<UUID> hidePlayers;
    private final List<UUID> seekPlayers;
    private long ticks, tempTicks;
    private final Random random;
    private Team hideTeam, seekTeam;

    public GameRunnable(Collection<Player> playerList, int maxSeekNum) {
        random = new Random();
        gameLifeCycle = GameLifeCycle.STARTING;
        hidePlayers = new ArrayList<>();
        seekPlayers = new ArrayList<>();
        initTeam(new ArrayList<>(playerList), maxSeekNum);
        ticks = 0;
        tempTicks = ticks;
    }

    private void initTeam(List<Player> playerList, int maxSeekNum) {
        resetTeam();
        for (int i = 0; i < maxSeekNum; i++) {
            int seek = random.nextInt(playerList.size());
            Player player = playerList.remove(seek);
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, -100, false, false, false));
            CrypticLib.platform().teleportPlayer(player, player.getWorld().getSpawnLocation());
            seekPlayers.add(player.getUniqueId());
            seekTeam.addPlayer(player);
        }
        for (Player player : playerList) {
            CrypticLib.platform().teleportPlayer(player, player.getWorld().getSpawnLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));
            hidePlayers.add(player.getUniqueId());
            hideTeam.addPlayer(player);
        }
    }

    @Override
    public void run() {
        if (tempTicks % 20 != 0) {
            tempTicks ++;
            ticks ++;
            return;
        }
        long timeSecond = tempTicks / 20;
        switch (gameLifeCycle) {
            case STARTING -> tickLifeCycleStarting(timeSecond);
            case PLAYING -> tickLifeCyclePlaying(timeSecond);
            case END -> tickLifeCycleEnd(timeSecond);
            case DEAD -> HideAndSeek.INSTANCE.endGame();
        }

        tempTicks ++;
        ticks ++;
    }

    private void tickLifeCycleEnd(long timeSecond) {
        if (timeSecond < 3)
            return;
        long countdown = GameLifeCycle.END.maxSecond - timeSecond;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Util.sendTitle(player, "&#FED8E2&l" + countdown, "plugin_message.game.end.subtitle");
        }
        if (timeSecond >= gameLifeCycle.maxSecond) {
            HideAndSeek.INSTANCE.endGame();
        }
    }

    private void tickLifeCyclePlaying(long timeSecond) {
        if (hidePlayers.isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Util.sendTitle(player, "", "plugin_message.game.playing.subtitle.all_hide_death");
            }
            setGameLifeCycle(GameLifeCycle.END);
            return;
        }
        if (timeSecond >= GameLifeCycle.PLAYING.maxSecond) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Util.sendTitle(player, "", "plugin_message.game.playing.subtitle.hide_win");
            }
            setGameLifeCycle(GameLifeCycle.END);
        }
    }

    private void tickLifeCycleStarting(long timeSecond) {
        int countdown = (int) (gameLifeCycle.maxSecond - timeSecond);
        Util.sendTitle(hidePlayers, "&#FED8E2&l" + countdown, "plugin_message.game.starting.subtitle.hide");
        Util.sendTitle(seekPlayers, "&#FED8E2&l" + countdown, "plugin_message.game.starting.subtitle.seek");

        if (timeSecond >= gameLifeCycle.maxSecond) {
            Util.sendTitle(hidePlayers, "", "plugin_message.game.starting.subtitle.end");
            Util.sendTitle(seekPlayers, "", "plugin_message.game.starting.subtitle.end");
            setGameLifeCycle(GameLifeCycle.PLAYING);
        }
    }

    public void resetTeam() {
        hideTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("hide");
        hideTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OWN_TEAM);
        seekTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("seek");
        seekTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OWN_TEAM);
    }

    public void removePlayer(UUID uuid) {
        if (seekPlayers.contains(uuid)) {
            seekTeam.removePlayer(Bukkit.getPlayer(uuid));
            seekPlayers.remove(uuid);
        } else {
            hideTeam.removePlayer(Bukkit.getPlayer(uuid));
            hidePlayers.remove(uuid);
        }
    }

    public GameLifeCycle gameLifeCycle() {
        return gameLifeCycle;
    }

    public void setGameLifeCycle(GameLifeCycle gameLifeCycle) {
        this.gameLifeCycle = gameLifeCycle;
        tempTicks = 0;
    }

    public List<UUID> hidePlayers() {
        return hidePlayers;
    }

    public List<UUID> seekPlayers() {
        return seekPlayers;
    }

    public long getTempTicks() {
        return tempTicks;
    }

    public enum GameLifeCycle {
        STARTING(20),
        PLAYING(900),
        END(10),
        DEAD(-1);

        private final long maxSecond;

        GameLifeCycle(long maxSecond) {
            this.maxSecond = maxSecond;
        }

        public long maxSecond() {
            return maxSecond;
        }

    }

    public enum GameTeam {
        HIDE, SEEK
    }

}
