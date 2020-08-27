package fr.il_totore.custompotion.api;

import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import fr.il_totore.spigotmetadata.api.nbt.*;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomPotionAPI {

    private static CustomPotionAPI api;

    private CustomEffectRegistry registry = new CustomEffectRegistry();
    private List<CustomEffectData> data = new ArrayList<>();
    private List<CustomEffectManager> managers = new ArrayList<>();
    private AreaEffectCloudManager cloudManager = new AreaEffectCloudManager();
    private NamespacedKey effectKey;

    public CustomPotionAPI(NamespacedKey effectKey){
        this.effectKey = effectKey;
    }

    public CustomEffectRegistry getRegistry() {
        return registry;
    }

    public Collection<CustomEffectData> getData() {
        return data;
    }

    public void addData(CustomEffectData data){
        this.data.add(data);
    }

    public void removeData(CustomEffectData data){
        this.data.remove(data);
    }

    public void removeData(OfflinePlayer player){
        removeData(getData(player, false));
    }

    public CustomEffectData getData(OfflinePlayer player, boolean registerIfAbsent){
        for(CustomEffectData d : data){
            if(d.getOfflinePlayer().getUniqueId().equals(player.getUniqueId())) return d;
        }
        if(!registerIfAbsent) return null;
        CustomEffectData d = new CustomEffectData(api, player);
        addData(d);
        return d;
    }

    public Collection<CustomEffectManager> getManagers() {
        return managers;
    }

    public void addManager(CustomEffectManager manager){
        this.managers.add(manager);
    }

    public void removeManager(CustomEffectManager manager){
        this.managers.remove(manager);
    }

    public void removeManager(OfflinePlayer player){
        removeManager(getManager(player));
    }

    public CustomEffectManager getManager(OfflinePlayer player){
        for(CustomEffectManager manager : managers){
            if(manager.getPlayer().getUniqueId().equals(player.getUniqueId())) return manager;
        }
        return null;
    }

    public boolean hasManager(OfflinePlayer player){
        return getManager(player) != null;
    }

    public AreaEffectCloudManager getCloudManager() {
        return cloudManager;
    }

    public NamespacedKey getEffectKey() {
        return effectKey;
    }

    public void save(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        NBTTagList list = nbtManager.of(NBTTagType.LIST, new LinkedList<>());
        for(CustomEffectData d : data){
            NBTTagCompound compound = nbtManager.of(NBTTagType.COMPOUND);
            d.save(compound);
            list.add(compound);
        }
        nbt.setTag("data", list);
        cloudManager.save(nbt);
    }

    public void load(NBTTagCompound nbt){
        NBTManager nbtManager = SpigotMetadataAPI.getAPI().getNBTManager();
        NBTTagList list = nbt.getList("data", NBTTagType.COMPOUND);
        for(NBTBase nbtBase : list.getTags(nbtManager)){
            CustomEffectData data = new CustomEffectData(this);
            data.load((NBTTagCompound) nbtBase);
            addData(data);
        }
    }

    public void tick() {
        managers.forEach(CustomEffectManager::tick);
    }

    public static CustomPotionAPI getAPI() {
        return api;
    }

    public static void setAPI(CustomPotionAPI api) {
        CustomPotionAPI.api = api;
    }
}
