package fr.il_totore.custompotion.api.item;

import fr.il_totore.custompotion.api.CustomPotionEffect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.PotionMeta;

public class PotionItemStack extends EffectItemStack {

    private CustomPotionEffect mainEffect;

    public PotionItemStack(Material material, short amount) {
        super(material, amount);
        if(material != Material.SPLASH_POTION && material != Material.LINGERING_POTION && material != Material.POTION) throw new IllegalArgumentException(material + " isn't a potion !");
    }

    public PotionItemStack(Material material) {
        this(material, (short) 1);
    }

    @Override
    public PotionMeta updateAppearance(NamespacedKey effectsLocation) {
        PotionMeta meta = (PotionMeta) super.updateAppearance(effectsLocation);
        if(mainEffect == null) return meta;
        meta.setColor(mainEffect.getType().getPotionColor());
        if(mainEffect.getType().isGlowing()) addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItemMeta(meta);
        return meta;
    }

    public CustomPotionEffect getMainEffect() {
        return mainEffect;
    }

    public void setMainEffect(CustomPotionEffect mainEffect) {
        this.mainEffect = mainEffect;
    }
}
