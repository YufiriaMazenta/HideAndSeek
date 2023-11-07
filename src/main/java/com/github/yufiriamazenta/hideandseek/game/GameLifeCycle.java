package com.github.yufiriamazenta.hideandseek.game;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;

public enum GameLifeCycle {
    STARTING(HideAndSeek.config().getLong("game_cycle.max_second.starting", 20)),
    PLAYING(HideAndSeek.config().getLong("game_cycle.max_second.playing", 900)),
    END(HideAndSeek.config().getLong("game_cycle.max_second.end", 10)),
    DEAD(HideAndSeek.config().getLong("game_cycle.max_second.starting", -1));

    private long maxSecond;

    GameLifeCycle(long maxSecond) {
        this.maxSecond = maxSecond;
    }

    public long maxSecond() {
        return maxSecond;
    }

    public void setMaxSecond(long maxSecond) {
        this.maxSecond = maxSecond;
    }

    public static void resetMaxSecond() {
        STARTING.setMaxSecond(HideAndSeek.config().getLong("game_cycle.max_second.starting", 20));
        PLAYING.setMaxSecond(HideAndSeek.config().getLong("game_cycle.max_second.playing", 900));
        END.setMaxSecond(HideAndSeek.config().getLong("game_cycle.max_second.end", 10));
        DEAD.setMaxSecond(HideAndSeek.config().getLong("game_cycle.max_second.starting", -1));
    }

}
