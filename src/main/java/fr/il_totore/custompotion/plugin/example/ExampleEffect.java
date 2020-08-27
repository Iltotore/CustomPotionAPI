package fr.il_totore.custompotion.plugin.example;

import fr.il_totore.custompotion.api.CustomEffectType;
import fr.il_totore.custompotion.api.CustomPotionEffect;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

@SuppressWarnings("deprecation")
public enum ExampleEffect implements CustomEffectType {

    REDSTONE(new NamespacedKey("example", "redstone"), ChatColor.RED + "Redstone", BarColor.RED, Color.RED,
            (player, effect) -> player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), effect.getAmplifier()*10, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.RED, 1))),
    REDSTONE2(new NamespacedKey("example", "redstone2"), ChatColor.BLUE + "Redstone2", BarColor.BLUE, Color.BLUE,
            (player, effect) -> player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation(), 1, 0.5, 0.5, 0.5, new Particle.DustOptions(Color.BLUE, 1)))
        ;

    private NamespacedKey key;
    private String name;
    private BarColor barColor;
    private Color potionColor;

    private BiConsumer<Player, CustomPotionEffect> startConsumer;
    private BiConsumer<Player, CustomPotionEffect> tickConsumer;
    private BiConsumer<Player, CustomPotionEffect> finishConsumer;

    ExampleEffect(NamespacedKey key, String name, BarColor barColor, Color potionColor, BiConsumer<Player, CustomPotionEffect> startConsumer, BiConsumer<Player, CustomPotionEffect> tickConsumer, BiConsumer<Player,CustomPotionEffect> finishConsumer){
        this.key = key;
        this.name = name;
        this.barColor = barColor;
        this.potionColor = potionColor;
        this.startConsumer = startConsumer;
        this.tickConsumer = tickConsumer;
        this.finishConsumer = finishConsumer;
    }

    ExampleEffect(NamespacedKey key, String name, BarColor barColor, Color potionColor, BiConsumer<Player, CustomPotionEffect> tickConsumer){
        this(key, name, barColor, potionColor, (player, effect) -> {}, tickConsumer, (player, effect) -> {});
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BarColor getBarColor() {
        return barColor;
    }

    @Override
    public Color getPotionColor() {
        return potionColor;
    }

    @Override
    public boolean isGlowing() {
        return true;
    }

    @Override
    public void start(Player player, CustomPotionEffect effect) {
        startConsumer.accept(player, effect);
    }

    @Override
    public void tick(Player player, CustomPotionEffect effect) {
        tickConsumer.accept(player, effect);
    }

    @Override
    public void finish(Player player, CustomPotionEffect effect) {
        finishConsumer.accept(player, effect);
    }
}
