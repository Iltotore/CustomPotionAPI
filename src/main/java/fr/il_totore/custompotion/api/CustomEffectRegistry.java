package fr.il_totore.custompotion.api;

import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomEffectRegistry {

    private List<CustomEffectType> customEffects = new ArrayList<>();
    
    public void register(CustomEffectType type){
        if(getType(type.getKey()) != null) throw new IllegalArgumentException("Effect type with key " + type.getKey() + " is already registered !");
        customEffects.add(type);
    }

    public void unregister(CustomEffectType type){
        customEffects.remove(type);
    }

    public CustomEffectType getType(NamespacedKey key){
        for(CustomEffectType type : customEffects){
            if(type.getKey().equals(key)) return type;
        }
        return null;
    }

    public Collection<CustomEffectType> getCustomTypes() {
        return customEffects;
    }
}