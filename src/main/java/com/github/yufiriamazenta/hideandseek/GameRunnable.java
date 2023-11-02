package com.github.yufiriamazenta.hideandseek;

import com.github.yufiriamazenta.hideandseek.cmd.HideAndSeekCmd;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameRunnable implements Runnable {

    private GameLifeCircle gameLifeCircle;
    private Map<UUID, GameCharacter> gameCharacterMap;
    private long ticks, tempTicks;
    private final Random random;

    public GameRunnable(Collection<Player> playerList, int maxSeekNum) {
        random = new Random();
        gameLifeCircle = GameLifeCircle.STARTING;
        gameCharacterMap = new ConcurrentHashMap<>();
        initCharacterMap(new ArrayList<>(playerList), maxSeekNum);
        ticks = 0;
        tempTicks = ticks;
    }

    private void initCharacterMap(List<Player> playerList, int maxSeekNum) {
        for (int i = 0; i < maxSeekNum; i++) {
            int seek = random.nextInt(playerList.size());
            Player seekPlayer = playerList.remove(seek);
            gameCharacterMap.put(seekPlayer.getUniqueId(), GameCharacter.SEEK);
            seekPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, false, false, false));
            seekPlayer.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, Integer.MAX_VALUE, 100, false, false, false));
        }
        for (Player player : playerList) {
            gameCharacterMap.put(player.getUniqueId(), GameCharacter.HIDE);
        }
    }

    @Override
    public void run() {
        //TODO
        if (tempTicks % 20 != 0) {
            return;
        }
        long timeSecond = tempTicks / 20;
        switch (gameLifeCircle) {
            case STARTING -> {
                startingTick(timeSecond);
            }
            case PLAYING -> {
                playingTick(timeSecond);
            }
            case END -> {
                endTick(timeSecond);
            }
        }

        tempTicks ++;
        ticks ++;
    }

    private void endTick(long timeSecond) {
        //TODO
        if (timeSecond >= gameLifeCircle.maxSecond) {
            HideAndSeek.INSTANCE.endGame();
        }
    }

    private void playingTick(long timeSecond) {
        //TODO
    }

    private void startingTick(long timeSecond) {
        for (UUID uuid : gameCharacterMap.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null)
                continue;
            int countdown = (int) (gameLifeCircle.maxSecond - timeSecond);
            if (gameCharacterMap.get(uuid).equals(GameCharacter.HIDE))
                player.sendTitle(String.valueOf(countdown), "plugin_message.game.starting.subtitle.hide");
            else {
                player.sendTitle(String.valueOf(countdown), "plugin_message.game.starting.subtitle.seek");
            }
        }
        if (timeSecond >= gameLifeCircle.maxSecond) {
            setGameLifeCircle(GameLifeCircle.PLAYING);
        }
    }

    public GameLifeCircle gameLifeCircle() {
        return gameLifeCircle;
    }

    public void setGameLifeCircle(GameLifeCircle gameLifeCircle) {
        this.gameLifeCircle = gameLifeCircle;
        tempTicks = 0;
    }

    public Map<UUID, GameCharacter> gameCharacterMap() {
        return gameCharacterMap;
    }

    public void gameCharacterMap(Map<UUID, GameCharacter> gameCharacterMap) {
        this.gameCharacterMap = gameCharacterMap;
    }

    public long getTempTicks() {
        return tempTicks;
    }

    public enum GameLifeCircle {
        STARTING(20),
        PLAYING(900),
        END(10),
        DEAD(-1);

        private final long maxSecond;

        GameLifeCircle(long maxSecond) {
            this.maxSecond = maxSecond;
        }

        public long maxSecond() {
            return maxSecond;
        }

    }

    public enum GameCharacter {
        HIDE, SEEK
    }

}
