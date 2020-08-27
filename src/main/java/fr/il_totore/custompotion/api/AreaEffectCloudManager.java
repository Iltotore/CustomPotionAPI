package fr.il_totore.custompotion.api;

import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import fr.il_totore.spigotmetadata.api.nbt.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.*;

public class AreaEffectCloudManager {

    private Map<UUID, List<CustomPotionEffect>> entities = new HashMap<>();

    public void addEntity(UUID uuid, List<CustomPotionEffect> effects){
        entities.put(uuid, effects);
    }

    public void removeEntity(UUID uuid){
        entities.remove(uuid);
    }

    public Collection<CustomPotionEffect> getEffects(UUID uuid){
        return entities.getOrDefault(uuid, Collections.emptyList());
    }

    public void tick(){
        List<UUID> toRemove = new ArrayList<>();
        for(Map.Entry<UUID, List<CustomPotionEffect>> entry : entities.entrySet()){
            Entity entity = Bukkit.getEntity(entry.getKey());
            if(entity == null || !entity.isValid()) toRemove.add(entry.getKey());
        }
        toRemove.forEach(this::removeEntity);
    }

    public void save(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        NBTTagList list = nbtManager.of(NBTTagType.LIST);
        for(Map.Entry<UUID, List<CustomPotionEffect>> entry : entities.entrySet()){
            NBTTagCompound compound = nbtManager.of(NBTTagType.COMPOUND);
            compound.setString("uuid", entry.getKey().toString());
            NBTTagList effectList = nbtManager.of(NBTTagType.LIST);
            for(CustomPotionEffect effect : entry.getValue()){
                NBTTagCompound effectCompound = nbtManager.of(NBTTagType.COMPOUND);
                effect.save(effectCompound);
                effectList.add(effectCompound);
            }
            compound.setTag("effects", effectList);
            list.add(compound);
        }
        nbt.setTag("areaData", list);
    }

    public void load(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        NBTTagList list = nbt.getList("areaData", NBTTagType.COMPOUND);
        Map<UUID, List<CustomPotionEffect>> areaEffects = new HashMap<>();
        for(NBTBase nbtBase : list.getTags(nbtManager)){
            NBTTagCompound data = (NBTTagCompound) nbtBase;
            List<CustomPotionEffect> effects = new ArrayList<>();
            NBTTagList effectList = data.getList("effects", NBTTagType.COMPOUND);
            for(NBTBase rawEffect : effectList.getTags(nbtManager)){
                CustomPotionEffect effect = new CustomPotionEffect();
                effect.load((NBTTagCompound) rawEffect);
                effects.add(effect);
            }
            areaEffects.put(UUID.fromString(nbt.getString("uuid")), effects);
        }
        this.entities = areaEffects;
    }
}
