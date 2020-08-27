package fr.il_totore.custompotion.api;

import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public interface CustomEffectType {

    NamespacedKey getKey();
    String getName();
    BarColor getBarColor();
    Color getPotionColor();
    boolean isGlowing();

    void start(Player player, CustomPotionEffect effect);
    void tick(Player player, CustomPotionEffect effect);
    void finish(Player player, CustomPotionEffect effect);
}
