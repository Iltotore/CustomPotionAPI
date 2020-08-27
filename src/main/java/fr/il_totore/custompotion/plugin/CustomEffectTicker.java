package fr.il_totore.custompotion.plugin;

import fr.il_totore.custompotion.api.CustomPotionAPI;

public class CustomEffectTicker implements Runnable {

    private CustomPotionAPI api;

    public CustomEffectTicker(CustomPotionAPI api) {
        this.api = api;
    }

    @Override
    public void run() {
        api.tick();
    }
}
