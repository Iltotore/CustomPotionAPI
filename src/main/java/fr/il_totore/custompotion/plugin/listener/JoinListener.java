package fr.il_totore.custompotion.plugin.listener;

import fr.il_totore.custompotion.api.CustomEffectData;
import fr.il_totore.custompotion.api.CustomEffectManager;
import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.plugin.CustomListener;
import fr.il_totore.custompotion.plugin.CustomPotionPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener extends CustomListener<PlayerJoinEvent> {

    private CustomPotionPlugin plugin;
    private CustomPotionAPI api;

    public JoinListener(CustomPotionPlugin plugin, CustomPotionAPI api) {
        this.plugin = plugin;
        this.api = api;
    }

    @Override
    public void run(PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            CustomEffectData data = api.getData(event.getPlayer(), true);
            CustomEffectManager manager = new CustomEffectManager(data, event.getPlayer());
            api.addManager(manager);
        }, 1);
    }
}
