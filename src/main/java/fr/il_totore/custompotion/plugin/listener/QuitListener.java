package fr.il_totore.custompotion.plugin.listener;

import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.plugin.CustomListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener extends CustomListener<PlayerQuitEvent> {

    private CustomPotionAPI api;

    public QuitListener(CustomPotionAPI api) {
        this.api = api;
    }

    @Override
    public void run(PlayerQuitEvent event) {
        api.removeManager(event.getPlayer());
    }
}
