package com.github.yufiriamazenta.hideandseek;

import crypticlib.util.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.UUID;

public class Util {

    public static void clearPlayerEffect(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public static void sendTitle(Player player, String titleKey, String subtitleKey) {
        String title = titleKey == null || titleKey.isEmpty() ? "" : HideAndSeek.config().getString(titleKey, titleKey);
        String subtitle = subtitleKey == null || subtitleKey.isEmpty() ? "" : HideAndSeek.config().getString(subtitleKey, subtitleKey);
        player.sendTitle(MsgUtil.color(title), MsgUtil.color(subtitle));
    }

    public static void sendTitle(List<UUID> players, String titleKey, String subtitleKey) {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null)
                continue;
            sendTitle(player, titleKey, subtitleKey);
        }
    }

}
