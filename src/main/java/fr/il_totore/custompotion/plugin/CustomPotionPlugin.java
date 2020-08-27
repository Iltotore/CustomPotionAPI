package fr.il_totore.custompotion.plugin;

import fr.il_totore.custompotion.api.CustomEffectData;
import fr.il_totore.custompotion.api.CustomEffectManager;
import fr.il_totore.custompotion.api.CustomEffectRegistry;
import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.plugin.command.CommandEffect;
import fr.il_totore.custompotion.plugin.example.ExampleEffect;
import fr.il_totore.custompotion.plugin.listener.*;
import fr.il_totore.spigotmetadata.api.SpigotMetadataAPI;
import fr.il_totore.spigotmetadata.api.nbt.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomPotionPlugin extends JavaPlugin {

    private CustomPotionAPI api;

    @Override
    public void onLoad() {
        api = new CustomPotionAPI(new NamespacedKey(this, "effects"));
        CustomPotionAPI.setAPI(api);
    }

    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(this, new CustomEffectTicker(api), 0, 1);

        registerEvent(PlayerItemConsumeEvent.class, new ConsumeListener(api));
        registerEvent(InventoryClickEvent.class, new InventoryClickListener());
        registerEvent(PlayerJoinEvent.class, new JoinListener(this, api));
        registerEvent(PlayerQuitEvent.class, new QuitListener(api));
        registerEvent(PotionSplashEvent.class, new SplashListener(api));

        getCommand("customeffect").setExecutor(new CommandEffect());

        CustomEffectRegistry registry = api.getRegistry();

        getLogger().info("Registering example effects...");
        for(ExampleEffect effect : ExampleEffect.values()) registry.register(effect);

        getLogger().info("Waiting for other plugins...");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {

            File file = new File(getDataFolder(), "save.dat");

            if(!getDataFolder().exists()) getDataFolder().mkdirs();

            if(file.exists()) {
                try {
                    getLogger().info("Loading save...");
                    NBTInputStream inputStream = new NBTInputStream(SpigotMetadataAPI.getAPI().getNBTManager(), new FileInputStream(file));
                    api.load((NBTTagCompound) inputStream.readNamedTag().getValue());
                    inputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    file.createNewFile();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            for(Player player : Bukkit.getOnlinePlayers())
                api.addManager(new CustomEffectManager(api.getData(player, true)));

        });
    }

    @Override
    public void onDisable() {
        File file = new File(getDataFolder(), "save.dat");

        if(!getDataFolder().exists()) getDataFolder().mkdirs();

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        getLogger().info("Saving potion effects...");
        try {
            NBTTagCompound nbt = SpigotMetadataAPI.getAPI().getNBTManager().of(NBTTagType.COMPOUND);
            api.save(nbt);
            System.out.println(nbt);
            NBTOutputStream outputStream = new NBTOutputStream(SpigotMetadataAPI.getAPI().getNBTManager(), new FileOutputStream(file));
            outputStream.writeNamedTag(new NamedNBT<>("effects", nbt));
            outputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        for(CustomEffectData data : api.getData()) data.clearEffects();
    }

    private <T extends Event> void registerEvent(Class<T> event, CustomListener<T> file) {
        Bukkit.getPluginManager().registerEvent(event, file, EventPriority.NORMAL, file, this);
    }
}
