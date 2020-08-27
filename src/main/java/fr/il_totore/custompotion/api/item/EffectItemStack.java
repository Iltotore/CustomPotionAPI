package fr.il_totore.custompotion.api.item;

import fr.il_totore.custompotion.api.CustomEffectManager;
import fr.il_totore.custompotion.api.CustomPotionEffect;
import fr.il_totore.custompotion.api.util.IOUtil;
import fr.il_totore.custompotion.api.util.RomanNumeral;
import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.util.*;

public class EffectItemStack extends ItemStack {

    public EffectItemStack(Material type, short amount){
        super(type, amount);
    }

    public EffectItemStack(Material type){
        super(type);
    }

    public EffectItemStack(ItemStack itemStack){
        super(itemStack);
    }

    public void addEffect(NamespacedKey location, CustomPotionEffect effect){
        List<CustomPotionEffect> effects = new ArrayList<>(getEffects(location));
        effects.add(effect);
        ItemMeta meta = getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        try {
            container.set(location, PersistentDataType.BYTE_ARRAY, IOUtil.serializeEffects(SpigotMetadataAPI.getAPI().getNBTManager(), effects));
            setItemMeta(meta);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<CustomPotionEffect> getEffects(NamespacedKey location){
        PersistentDataContainer container = getItemMeta().getPersistentDataContainer();
        if(!container.has(location, PersistentDataType.BYTE_ARRAY)) return Collections.emptyList();
        try {
            return IOUtil.deserializeEffects(SpigotMetadataAPI.getAPI().getNBTManager(), container.get(location, PersistentDataType.BYTE_ARRAY));
        } catch(IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public ItemMeta updateAppearance(NamespacedKey effectsLocation){
        ItemMeta meta = getItemMeta();
        List<String> lines = new ArrayList<>();
        for(CustomPotionEffect effect : getEffects(effectsLocation)){
            lines.add(effect.getType().getName() + " " + new RomanNumeral(effect.getAmplifier()+1).toString() + " (" + CustomEffectManager.DATE_FORMAT.format(new Date(effect.getDuration() * 50)) + ")");
        }
        meta.setLore(lines);
        setItemMeta(meta);
        return meta;
    }

}
