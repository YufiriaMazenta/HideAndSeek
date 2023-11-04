package com.github.yufiriamazenta.hideandseek;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.annotations.BukkitListener;
import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

@BukkitListener
public class GameListener implements Listener {

    @EventHandler
    public void onHurt(EntityDamageEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning()) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            event.setCancelled(true);
            return;
        }
        if (HideAndSeek.INSTANCE.gameRunnable().seekPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDroppedExp(0);
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
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
    public void onDisguise(DisguiseEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));
    }

    @EventHandler
    public void onUnDisguise(UndisguiseEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        //TODO
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
    }

}
