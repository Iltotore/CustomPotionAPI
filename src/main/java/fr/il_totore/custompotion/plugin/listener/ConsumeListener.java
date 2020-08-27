package fr.il_totore.custompotion.plugin.listener;

import fr.il_totore.custompotion.api.CustomEffectData;
import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.api.item.EffectItemStack;
import fr.il_totore.custompotion.plugin.CustomListener;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class ConsumeListener extends CustomListener<PlayerItemConsumeEvent> {

    private CustomPotionAPI api;

    public ConsumeListener(CustomPotionAPI api){
        this.api = api;
    }

    @Override
    public void run(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(!item.hasItemMeta()) return;
        EffectItemStack effectItem = new EffectItemStack(item);
        CustomEffectData data = api.getData(player, false);
        data.addPotionEffects(effectItem.getEffects(api.getEffectKey()), false);
    }
}
