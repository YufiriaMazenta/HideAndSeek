package com.github.yufiriamazenta.hideandseek;

import com.github.yufiriamazenta.hideandseek.HideAndSeek;
import crypticlib.annotations.BukkitListener;
import crypticlib.util.MsgUtil;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.*;
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
        if (!(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
            return;
        }
        if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
            return;
        }
        if (HideAndSeek.INSTANCE.gameRunnable().seekPlayers().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAirChange(EntityAirChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onHurtByEntity(EntityDamageByEntityEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning()) {
            event.setCancelled(true);
            return;
        }
        if (HideAndSeek.INSTANCE.gameRunnable().hidePlayers().contains(event.getDamager().getUniqueId())) {
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
            event.getEntity().sendTitle("", MsgUtil.color(HideAndSeek.config().getString("plugin_message.game.playing.subtitle.hide_death", "")));
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
        if (event.getDisguise() instanceof MiscDisguise)
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false, false));
    }

    @EventHandler
    public void onUnDisguise(UndisguiseEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player player) {
            if (!player.getGameMode().equals(GameMode.CREATIVE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPickArrow(PlayerPickupArrowEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
            event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        GameRunnable gameRunnable = HideAndSeek.INSTANCE.gameRunnable();
        if (!gameRunnable.hidePlayers().contains(event.getPlayer().getUniqueId())) {
            if (!gameRunnable.seekPlayers().contains(event.getPlayer().getUniqueId())) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
            return;
        }
        if (gameRunnable.hidePlayerDisguiseMap().containsKey(event.getPlayer().getUniqueId())) {
            Disguise disguise = gameRunnable.hidePlayerDisguiseMap().get(event.getPlayer().getUniqueId());
            disguise.setEntity(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
    }

}
