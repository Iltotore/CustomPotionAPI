package fr.il_totore.custompotion.api;

import fr.il_totore.custompotion.api.util.RomanNumeral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomEffectManager {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("mm:ss");

    private Player player;
    private CustomEffectData data;
    private Map<CustomEffectType, BossBar> progressBars = new HashMap<>();

    public CustomEffectManager(CustomEffectData data, Player player) {
        assert data.getOfflinePlayer().getPlayer() != null;
        this.player = player;
        this.data = data;
        for(CustomPotionEffect effect : data.getPotionEffects()) addEffect(effect, true);
    }

    public CustomEffectManager(CustomEffectData data){
        this(data, data.getOfflinePlayer().getPlayer());
    }

    public void tick() {
        List<CustomPotionEffect> toRemove = new ArrayList<>();
        for(CustomPotionEffect effect : data.getPotionEffects()) {
            effect.tick(player);
            renderEffect(effect);
            if(effect.getActualDuration() == 0) toRemove.add(effect);
        }
        data.removePotionEffects(toRemove);
    }

    public void addEffect(CustomPotionEffect effect, boolean force){
        if(!progressBars.containsKey(effect.getType())){
            BossBar bar = Bukkit.createBossBar(effect.getType().getName(), effect.getType().getBarColor(), BarStyle.SOLID);
            bar.setVisible(true);
            bar.addPlayer(player);
            progressBars.put(effect.getType(), bar);
        }
    }

    public void removeEffect(CustomPotionEffect effect){
        BossBar bar = progressBars.remove(effect.getType());
        if(bar != null) bar.removeAll();
    }

    public void renderEffect(CustomPotionEffect effect){
        BossBar bar = progressBars.get(effect.getType());
        if(bar == null) return;
        bar.setTitle(effect.getType().getName() + " " + new RomanNumeral(effect.getAmplifier() + 1).toString() + " " + ChatColor.YELLOW + DATE_FORMAT.format(new Date(effect.getActualDuration()*50)));
        bar.setProgress(((double)effect.getActualDuration())/((double)effect.getDuration()));
    }

    public Player getPlayer() {
        return player;
    }

    public CustomEffectData getData() {
        return data;
    }

    public Optional<BossBar> getProgressBar(CustomEffectType type){
        return Optional.ofNullable(progressBars.get(type));
    }
}
