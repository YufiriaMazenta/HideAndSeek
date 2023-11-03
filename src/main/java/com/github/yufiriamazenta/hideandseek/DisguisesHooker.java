package com.github.yufiriamazenta.hideandseek;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DisguisesHooker {

    private final static Map<String, Function<Player, Disguise>> disguiseFuncMap;

    static {
        disguiseFuncMap = new ConcurrentHashMap<>();
        disguiseFuncMap.put("crafting_table", player -> {
            MiscDisguise disguise = new MiscDisguise(DisguiseType.FALLING_BLOCK, Material.CRAFTING_TABLE);
            disguise.setEntity(player);
            disguise.startDisguise();
            return disguise;
        });
        disguiseFuncMap.put("lantern", player -> {
            MiscDisguise disguise = new MiscDisguise(DisguiseType.FALLING_BLOCK, Material.LANTERN);
            disguise.setEntity(player);
            disguise.startDisguise();
            return disguise;
        });
    }

    public static Disguise disguises(Player player, String type) {
        return disguiseFuncMap.get(type).apply(player);
    }

    public static Map<String, Function<Player, Disguise>> disguiseFuncMap() {
        return disguiseFuncMap;
    }

}
