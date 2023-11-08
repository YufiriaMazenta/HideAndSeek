package com.github.yufiriamazenta.hideandseek.game;

import com.github.yufiriamazenta.hideandseek.DisguisesHooker;
import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import com.github.yufiriamazenta.hideandseek.Util;
import crypticlib.CrypticLib;
import crypticlib.util.MsgUtil;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameRunnable implements Runnable {

    private GameLifeCycle gameLifeCycle;
    private List<UUID> hidePlayers;
    private Map<UUID, Boolean> hidePlayerLockedMap;
    private Map<GameLifeCycle, Map<UUID, Disguise>> hidePlayerDisguiseMap;
    private List<UUID> seekPlayers;
    private long ticks, tempTicks;
    private Random random;
    private GameMap gameMap;//todo
    private Team hideTeam, seekTeam;
    private BossBar timeBossBar;

    public GameRunnable(Collection<Player> playerList, int maxSeekNum, GameMap map) {
        this.gameMap = map;
        initGame();


        timeBossBar = Bukkit.createBossBar(
                new NamespacedKey(HideAndSeek.INSTANCE, "time"),
                MsgUtil.color(HideAndSeek.config().getString("plugin_message.game.boss_bar.title", "%time%")),
                BarColor.PINK,
                BarStyle.SOLID,
                BarFlag.CREATE_FOG
        );
        for (Player player : playerList) {
            timeBossBar.addPlayer(player);
        }

        initTeam(new ArrayList<>(playerList), maxSeekNum);
        ticks = 0;
        tempTicks = ticks;
    }

    private void initGame() {
        random = new Random();
        gameLifeCycle = GameLifeCycle.STARTING;
        hidePlayers = new ArrayList<>();
        hidePlayerDisguiseMap = new ConcurrentHashMap<>();
        hidePlayerLockedMap = new ConcurrentHashMap<>();
        seekPlayers = new ArrayList<>();
    }

    private void initTeam(List<Player> playerList, int maxSeekNum) {
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hide") != null)
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("hide").unregister();
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("seek") != null)
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("seek").unregister();

        hideTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("hide");
        hideTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        hideTeam.setColor(ChatColor.AQUA);
        seekTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("seek");
        seekTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        seekTeam.setColor(ChatColor.RED);

        for (Player player : playerList) {
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }

        for (int i = 0; i < maxSeekNum; i++) {
            int seek = random.nextInt(playerList.size());
            Player player = playerList.remove(seek);
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -100, false, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 100, false, false, false));
            CrypticLib.platform().teleportEntity(player, player.getWorld().getSpawnLocation());
            seekPlayers.add(player.getUniqueId());
            seekTeam.addPlayer(player);
        }
        for (Player player : playerList) {
            CrypticLib.platform().teleportEntity(player, player.getWorld().getSpawnLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));
            player.setAllowFlight(false);
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
        long remainingSecond = gameLifeCycle.maxSecond() - timeSecond;
        String bossBarTitle = MsgUtil.color(
                HideAndSeek.config().getString("plugin_message.game.boss_bar.title", "%time%")
                        .replace("%time%", "" + remainingSecond)
        );
        timeBossBar.setTitle(bossBarTitle);
        timeBossBar.setProgress((double) timeSecond / gameLifeCycle.maxSecond());
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
        long countdown = GameLifeCycle.END.maxSecond() - timeSecond;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Util.sendTitle(player, "&#FED8E2&l" + countdown, "plugin_message.game.end.subtitle");
        }
        if (timeSecond >= gameLifeCycle.maxSecond()) {
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
        if (seekPlayers.isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Util.sendTitle(player, "", "plugin_message.game.playing.subtitle.all_seek_death");
            }
            setGameLifeCycle(GameLifeCycle.END);
            return;
        }
        if (timeSecond >= GameLifeCycle.PLAYING.maxSecond()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Util.sendTitle(player, "", "plugin_message.game.playing.subtitle.hide_win");
            }
            setGameLifeCycle(GameLifeCycle.END);
        }
    }

    private void tickLifeCycleStarting(long timeSecond) {
        if (timeSecond == 0) {
            for (UUID uuid : hidePlayers) {
                if (hidePlayerDisguiseMap.containsKey(uuid))
                    continue;
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    continue;
            }
            for (UUID uuid : seekPlayers) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    continue;
                player.getInventory().addItem(new ItemStack(Material.BOW));
                player.getInventory().addItem(new ItemStack(Material.ARROW, (int) (GameLifeCycle.PLAYING.maxSecond() / 20)));
            }
        }

        int countdown = (int) (gameLifeCycle.maxSecond() - timeSecond);
        Util.sendTitle(hidePlayers, "&#FED8E2&l" + countdown, "plugin_message.game.starting.subtitle.hide");
        Util.sendTitle(seekPlayers, "&#FED8E2&l" + countdown, "plugin_message.game.starting.subtitle.seek");

        if (timeSecond >= gameLifeCycle.maxSecond()) {
            Util.sendTitle(hidePlayers, "", "plugin_message.game.starting.subtitle.end");
            Util.sendTitle(seekPlayers, "", "plugin_message.game.starting.subtitle.end");
            for (UUID uuid : hidePlayers) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    continue;
                Util.clearPlayerEffect(player);
            }
            for (UUID uuid : seekPlayers) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null)
                    continue;
                Util.clearPlayerEffect(player);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3, false, false, false));
            }
            setGameLifeCycle(GameLifeCycle.PLAYING);
        }
    }

    public void removePlayer(UUID uuid) {
        if (seekPlayers.contains(uuid)) {
            seekTeam.removePlayer(Bukkit.getPlayer(uuid));
            seekPlayers.remove(uuid);
        } else {
            hideTeam.removePlayer(Bukkit.getPlayer(uuid));
//            Disguise disguise = hidePlayerDisguiseMap.remove(uuid);
//            if (disguise != null)
//                disguise.removeDisguise();
            //TODO
            hidePlayerLockedMap.remove(uuid);
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

    public long tempTicks() {
        return tempTicks;
    }

    public Map<UUID, Disguise> hidePlayerDisguiseMap() {
        throw new UnsupportedOperationException("");
    }

    public BossBar timeBossBar() {
        return timeBossBar;
    }

    public void setTimeBossBar(BossBar timeBossBar) {
        this.timeBossBar = timeBossBar;
    }

    public Map<UUID, Boolean> hidePlayerLockedMap() {
        return hidePlayerLockedMap;
    }

}
