package com.github.yufiriamazenta.hideandseek;

import crypticlib.CrypticLib;
import crypticlib.annotations.BukkitListener;
import crypticlib.util.MsgUtil;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
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
            event.getEntity().setAllowFlight(false);
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
    public void onHideSwapHandLock(PlayerSwapHandItemsEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        GameRunnable gameRunnable = HideAndSeek.INSTANCE.gameRunnable();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!gameRunnable.hidePlayers().contains(uuid))
            return;
        if (gameRunnable.hidePlayerLockedMap().containsKey(uuid) && gameRunnable.hidePlayerLockedMap().get(uuid)) {
            player.setAllowFlight(false);
            player.setFlying(false);
            gameRunnable.hidePlayerLockedMap().put(uuid, false);
            return;
        }
        gameRunnable.hidePlayerLockedMap().put(uuid, true);
        player.setAllowFlight(true);
        player.setFlying(true);

        if (gameRunnable.hidePlayerDisguiseMap().containsKey(uuid)) {
            Disguise disguise = gameRunnable.hidePlayerDisguiseMap().get(uuid);
            if (disguise instanceof MiscDisguise) {
                Location location = player.getLocation().clone();
                location.setX(location.getBlockX() + 0.5);
                location.setZ(location.getBlockZ() + 0.5);
                location.setY(location.getBlockY());
                CrypticLib.platform().teleportPlayer(player, location);
            }
        }
    }

    @EventHandler
    public void onHideMove(PlayerMoveEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        GameRunnable gameRunnable = HideAndSeek.INSTANCE.gameRunnable();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!gameRunnable.hidePlayerLockedMap().containsKey(uuid))
            return;
        if (gameRunnable.hidePlayerLockedMap().get(uuid)) {
            double fromX = event.getFrom().getX();
            double fromY = event.getFrom().getY();
            double fromZ = event.getFrom().getZ();
            if (event.getTo() == null)
                return;
            double toX = event.getTo().getX();
            double toY = event.getTo().getY();
            double toZ = event.getTo().getZ();
            if (!(fromX == toX && fromY == toY && fromZ == toZ))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSeekSwapHand(PlayerSwapHandItemsEvent event) {
        if (!HideAndSeek.INSTANCE.isGameRunning())
            return;
        GameRunnable gameRunnable = HideAndSeek.INSTANCE.gameRunnable();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!gameRunnable.seekPlayers().contains(uuid))
            return;

        int attackNum = 0;
        for (Entity entity : player.getNearbyEntities(
                HideAndSeek.config().getDouble("seek_swap_skill.radius.x"),
                HideAndSeek.config().getDouble("seek_swap_skill.radius.y"),
                HideAndSeek.config().getDouble("seek_swap_skill.radius.z")
        )) {
            UUID uuid1 = entity.getUniqueId();
            if (gameRunnable.hidePlayers().contains(uuid1)) {
                player.attack(entity);
                attackNum ++;
            }
        }
        double damage;
        if (attackNum > 0) {
            damage = HideAndSeek.config().getDouble("seek_swap_skill.hit_damage", 2);
        } else {
            damage = HideAndSeek.config().getDouble("seek_swap_skill.miss_damage", 1);
        }
        player.setHealth(Math.max(player.getHealth() - damage, 0));
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
