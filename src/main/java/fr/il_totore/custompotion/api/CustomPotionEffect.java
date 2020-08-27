package fr.il_totore.custompotion.api;

import fr.il_totore.spigotmetadata.api.nbt.NBTTagCompound;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public class CustomPotionEffect {

    private long actualDuration;
    private long duration;
    private short amplifier;
    private CustomEffectType type;

    public CustomPotionEffect(){

    }

    public CustomPotionEffect(CustomEffectType type, long duration, short amplifier) {
        this.duration = duration;
        this.actualDuration = duration;
        this.amplifier = amplifier;
        this.type = type;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(long actualDuration) {
        this.actualDuration = actualDuration;
    }

    public short getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(short amplifier) {
        this.amplifier = amplifier;
    }

    public CustomEffectType getType() {
        return type;
    }

    public void setType(CustomEffectType type) {
        this.type = type;
    }

    public void save(NBTTagCompound tag){
        tag.setLong("duration", duration);
        tag.setLong("actualDuration", actualDuration);
        tag.setShort("amplifier", amplifier);
        tag.setString("type", type.getKey().toString());
    }

    @SuppressWarnings("deprecation")
    public void load(NBTTagCompound tag) {
        this.actualDuration = tag.getLong("actualDuration");
        this.duration = tag.getLong("duration");
        this.amplifier = tag.getShort("amplifier");
        String[] rawKey = tag.getString("type").split(":");
        this.type = CustomPotionAPI.getAPI().getRegistry().getType(new NamespacedKey(rawKey[0], rawKey[1]));
    }

    public void tick(Player player){
        actualDuration--;
        type.tick(player, this);
    }


}
