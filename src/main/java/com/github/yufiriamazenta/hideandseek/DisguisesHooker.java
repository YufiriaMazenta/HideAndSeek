package com.github.yufiriamazenta.hideandseek;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class DisguisesHooker {

    private final static Map<String, Function<Player, Disguise>> disguiseFuncMap;

    static {
        disguiseFuncMap = new ConcurrentHashMap<>();
        regDisguise("crafting_table", player -> genBlockDisguise(player, Material.CRAFTING_TABLE));
        regDisguise("hay_block", player -> genBlockDisguise(player, Material.HAY_BLOCK));
        regDisguise("jack_o_lantern", player -> genBlockDisguise(player, Material.JACK_O_LANTERN));
        regDisguise("smithing_table", player -> genBlockDisguise(player, Material.SMITHING_TABLE));
        regDisguise("grindstone", player -> genBlockDisguise(player, Material.GRINDSTONE));
        regDisguise("fletching_table", player -> genBlockDisguise(player, Material.FLETCHING_TABLE));
        regDisguise("campfire", player -> genBlockDisguise(player, Material.CAMPFIRE));
        regDisguise("bee_nest", player -> genBlockDisguise(player, Material.BEE_NEST));
        regDisguise("lantern", player -> genBlockDisguise(player, Material.LANTERN));
        regDisguise("dandelion", player -> genBlockDisguise(player, Material.DANDELION));
        regDisguise("poppy", player -> genBlockDisguise(player, Material.POPPY));
        regDisguise("blue_orchid", player -> genBlockDisguise(player, Material.BLUE_ORCHID));
        regDisguise("allium", player -> genBlockDisguise(player, Material.ALLIUM));
        regDisguise("rose_bush", player -> genBlockDisguise(player, Material.ROSE_BUSH));
        regDisguise("grass", player -> genBlockDisguise(player, Material.GRASS));
        regDisguise("amethyst_cluster", player -> genBlockDisguise(player, Material.AMETHYST_CLUSTER));
        regDisguise("cake", player -> genBlockDisguise(player, Material.CAKE));
        regDisguise("boat", player -> genMiscDisguise(player, DisguiseType.BOAT));

        regDisguise("cod", player -> genMobDisguise(player, DisguiseType.COD));
        regDisguise("salmon", player -> genMobDisguise(player, DisguiseType.SALMON));
        regDisguise("tropical", player -> genMobDisguise(player, DisguiseType.TROPICAL_FISH));
        regDisguise("axolotl", player -> genMobDisguise(player, DisguiseType.AXOLOTL));
        regDisguise("sheep", player -> genMobDisguise(player, DisguiseType.SHEEP));
        regDisguise("pig", player -> genMobDisguise(player, DisguiseType.PIG));
        regDisguise("cow", player -> genMobDisguise(player, DisguiseType.COW));
        regDisguise("bee", player -> genMobDisguise(player, DisguiseType.BEE));
        regDisguise("squid", player -> genMobDisguise(player, DisguiseType.SQUID));
    }

    public static Disguise disguises(Player player, String type) {
        return disguiseFuncMap.get(type).apply(player);
    }

    public static void regDisguise(String key, Function<Player, Disguise> disguiseFunction) {
        disguiseFuncMap.put(key, disguiseFunction);
    }

    public static Map<String, Function<Player, Disguise>> disguiseFuncMap() {
        return disguiseFuncMap;
    }

    private static MobDisguise genMobDisguise(Player player, DisguiseType type) {
        MobDisguise disguise = new MobDisguise(type);
        disguise.setEntity(player);
        disguise.startDisguise();
        return disguise;
    }

    private static MiscDisguise genBlockDisguise(Player player, Material material) {
        MiscDisguise disguise = new MiscDisguise(DisguiseType.FALLING_BLOCK, material);
        disguise.setEntity(player);
        disguise.startDisguise();
        return disguise;
    }

    private static MiscDisguise genMiscDisguise(Player player, DisguiseType disguiseType) {
        MiscDisguise disguise = new MiscDisguise(disguiseType);
        disguise.setEntity(player);
        disguise.startDisguise();
        return disguise;
    }

    public static List<String> getDisguises() {
        return new ArrayList<>(disguiseFuncMap.keySet());
    }

}
