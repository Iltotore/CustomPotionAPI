package fr.il_totore.custompotion.api.util;

import fr.il_totore.custompotion.api.CustomPotionEffect;
import fr.il_totore.spigotmetadata.api.nbt.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IOUtil {

    public static byte[] serializeEffects(NBTManager nbtManager, Collection<CustomPotionEffect> effects) throws IOException {
        NBTTagList list = nbtManager.of(NBTTagType.LIST);
        for(CustomPotionEffect effect : effects){
            NBTTagCompound nbtTagCompound = nbtManager.of(NBTTagType.COMPOUND);
            effect.save(nbtTagCompound);
            list.add(nbtTagCompound);
        }
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        NBTOutputStream outputStream = new NBTOutputStream(nbtManager, byteStream);
        outputStream.writeTag(list);
        outputStream.close();
        byteStream.close();
        return byteStream.toByteArray();
    }

    public static Collection<CustomPotionEffect> deserializeEffects(NBTManager nbtManager, byte[] bytes) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        NBTInputStream inputStream = new NBTInputStream(nbtManager, byteStream);
        NBTTagList list = (NBTTagList) inputStream.readTag();
        List<CustomPotionEffect> effects = new ArrayList<>();
        for(NBTBase nbtBase : list.getTags(nbtManager)){
            CustomPotionEffect effect = new CustomPotionEffect();
            effect.load((NBTTagCompound) nbtBase);
            if(effect.getType() == null) continue;
            effects.add(effect);
        }
        inputStream.close();
        return effects;
    }
}
