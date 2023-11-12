package com.github.yufiriamazenta.hideandseek;

import org.bukkit.configuration.file.YamlConfiguration;

public class Configs {

    public static String gameSettingsTimeBarColor;
    public static String gameSettingsTimeBarTitle;

    public static Double gameSettingsSeekSwapSkillRadiusX;
    public static Double gameSettingsSeekSwapSkillRadiusY;
    public static Double gameSettingsSeekSwapSkillRadiusZ;
    public static Double gameSettingsSeekSwapSkillHitDamage;
    public static Double gameSettingsSeekSwapSkillMissDamage;

    static void reloadConfigs() {
        HideAndSeek.INSTANCE.reloadConfig();
        YamlConfiguration config = (YamlConfiguration) HideAndSeek.config();
        gameSettingsTimeBarColor = config.getString("game_settings.time_bar.color", "red");
        gameSettingsTimeBarTitle = config.getString("game_settings.time_bar.title", "&#FED8E2&l当前阶段剩余时间: &a&l%time% &#FED8E2&l秒");
        gameSettingsSeekSwapSkillRadiusX = config.getDouble("seek_swap_skill.radius.x", 2);
        gameSettingsSeekSwapSkillRadiusY = config.getDouble("seek_swap_skill.radius.y", 2);
        gameSettingsSeekSwapSkillRadiusZ = config.getDouble("seek_swap_skill.radius.z", 2);
        gameSettingsSeekSwapSkillHitDamage = config.getDouble("seek_swap_skill.hit_damage", 2);
        gameSettingsSeekSwapSkillMissDamage = config.getDouble("seek_swap_skill.miss_damage", 2);
    }

}
