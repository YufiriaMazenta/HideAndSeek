package com.github.yufiriamazenta.hideandseek.listener;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.annotations.BukkitListener;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

@BukkitListener
public class GameListener implements Listener {

    @EventHandler
    public void onHurt(EntityDamageEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            event.setCancelled(true);
        if (!(event.getEntity() instanceof Player player))
            return;
        if (HideAndSeek.INSTANCE.gameRunnable().seekPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        UUID uuid = event.getEntity().getUniqueId();
        if (HideAndSeek.INSTANCE.gameRunnable().hidePlayers().contains(uuid)) {
            HideAndSeek.INSTANCE.gameRunnable().removePlayer(uuid);
            event.getEntity().sendTitle("", HideAndSeek.config().getString("plugin_message.game.playing.subtitle.hide_death"));
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        //TODO
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        //TODO
    }

}
