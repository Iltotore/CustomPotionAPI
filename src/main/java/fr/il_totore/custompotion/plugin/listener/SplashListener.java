package fr.il_totore.custompotion.plugin.listener;

import fr.il_totore.custompotion.api.CustomEffectData;
import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.api.CustomPotionEffect;
import fr.il_totore.custompotion.api.util.IOUtil;
import fr.il_totore.custompotion.plugin.CustomListener;
import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.Collection;

public class SplashListener extends CustomListener<PotionSplashEvent> {

    private CustomPotionAPI api;

    public SplashListener(CustomPotionAPI api) {
        this.api = api;
    }

    @Override
    public void run(PotionSplashEvent event) {
        ItemStack item = event.getEntity().getItem();
        if(!item.hasItemMeta()) return;

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if(!container.has(api.getEffectKey(), PersistentDataType.BYTE_ARRAY)) return;

        for(Entity entity : event.getAffectedEntities()) {
            if(!(entity instanceof Player)) continue;
            Player player = (Player) entity;
            double intensity = event.getIntensity(player);

            CustomEffectData data = api.getData(player, false);
            try {
                Collection<CustomPotionEffect> effects = IOUtil.deserializeEffects(SpigotMetadataAPI.getAPI().getNBTManager(), container.get(api.getEffectKey(), PersistentDataType.BYTE_ARRAY));
                for(CustomPotionEffect effect : effects) {
                    if(effect.getType() == null) continue;
                    data.addPotionEffect(new CustomPotionEffect(effect.getType(), (long) (effect.getDuration()*intensity), effect.getAmplifier()), true);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
