package fr.il_totore.custompotion.api;

import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import fr.il_totore.spigotmetadata.api.nbt.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class CustomEffectData {

    private CustomPotionAPI api;
    private OfflinePlayer offlinePlayer;
    private List<CustomPotionEffect> potionEffects = new ArrayList<>();

    public CustomEffectData(CustomPotionAPI api){
        this.api = api;
    }

    public CustomEffectData(CustomPotionAPI api, OfflinePlayer offlinePlayer){
        this(api);
        this.offlinePlayer = offlinePlayer;
    }

    public void addPotionEffect(CustomPotionEffect effect, boolean force){
        if(effect.getDuration() <= 0) throw new IllegalArgumentException("Duration must be positive");
        CustomPotionEffect oldEffect = getEffect(effect.getType());
        if(oldEffect == null) {
            potionEffects.add(effect);
        } else if(force || effect.getAmplifier() > oldEffect.getAmplifier() || (effect.getAmplifier() == oldEffect.getAmplifier() && effect.getDuration() > oldEffect.getDuration())){
            oldEffect.setDuration(effect.getDuration());
            oldEffect.setActualDuration(effect.getActualDuration());
            oldEffect.setAmplifier(effect.getAmplifier());
        }
        if(api.hasManager(offlinePlayer)) {
            api.getManager(offlinePlayer).addEffect(effect, force);
        }
    }

    public void addPotionEffects(Collection<CustomPotionEffect> effects, boolean force) {
        effects.forEach(effect -> addPotionEffect(effect, force));
    }

    public void removePotionEffect(CustomPotionEffect effect){
        if(offlinePlayer.isOnline()) api.getManager(offlinePlayer.getPlayer()).removeEffect(effect);
        potionEffects.remove(effect);
    }

    public void removePotionEffects(Collection<CustomPotionEffect> effects) {
        new ArrayList<>(effects).forEach(this::removePotionEffect);
    }

    public void clearEffects() {
        removePotionEffects(getPotionEffects());
    }

    public Collection<CustomPotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public boolean hasEffect(CustomEffectType type){
        return getEffect(type) != null;
    }

    public CustomPotionEffect getEffect(CustomEffectType type){
        for(CustomPotionEffect effect : potionEffects){
            if(effect.getType().getKey().equals(type.getKey())) return effect;
        }
        return null;
    }

    public OfflinePlayer getOfflinePlayer() {
        return offlinePlayer;
    }

    public void save(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        nbt.setString("uuid", offlinePlayer.getUniqueId().toString());
        NBTTagList list = nbtManager.of(NBTTagType.LIST, new LinkedList<>());
        for(CustomPotionEffect effect : potionEffects){
            NBTTagCompound effectNBT = nbtManager.of(NBTTagType.COMPOUND);
            effect.save(effectNBT);
            list.add(effectNBT);
        }
        nbt.setTag("effects", list);
    }

    public void load(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(nbt.getString("uuid")));
        NBTTagList list = nbt.getList("effects", NBTTagType.COMPOUND);
        for(NBTBase nbtBase : list.getTags(nbtManager)){
            CustomPotionEffect effect = new CustomPotionEffect();
            effect.load((NBTTagCompound) nbtBase);
            addPotionEffect(effect, true);
        }
    }
}
