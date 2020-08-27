package fr.il_totore.custompotion.plugin.command;

import fr.il_totore.custompotion.api.CustomEffectType;
import fr.il_totore.custompotion.api.CustomPotionAPI;
import fr.il_totore.custompotion.api.CustomPotionEffect;
import fr.il_totore.custompotion.api.item.PotionItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class SubCommandPotion implements CommandExecutor {

    private Map<String, Material> map = new HashMap<>();

    {
        map.put("normal", Material.POTION);
        map.put("splash", Material.SPLASH_POTION);
        map.put("lingering", Material.LINGERING_POTION);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        CustomPotionAPI api = CustomPotionAPI.getAPI();

        String[] rawKey = args[1].split(":");
        NamespacedKey key = new NamespacedKey(rawKey[0], rawKey[1]);
        CustomEffectType type = api.getRegistry().getType(key);
        long duration = Long.parseLong(args[2])*20;
        short amplifier = args.length < 4 ? 0 : Short.parseShort(args[3]);

        if(type == null){
            sender.sendMessage(ChatColor.RED + "Unknown effect: " + args[1]);
            return false;
        }

        CustomPotionEffect effect = new CustomPotionEffect(type, duration, amplifier);

        if(args.length > 4) System.out.println(args[4]);

        PotionItemStack potion = new PotionItemStack(args.length > 4 ? map.getOrDefault(args[4].toLowerCase(), Material.POTION) : Material.POTION);
        potion.addEffect(api.getEffectKey(), effect);
        potion.setMainEffect(effect);
        ItemMeta meta = potion.updateAppearance(api.getEffectKey());

        switch (potion.getType()){
            case SPLASH_POTION:
                meta.setDisplayName(ChatColor.WHITE + "Splash Potion of " + effect.getType().getName());
                break;
            case LINGERING_POTION:
                meta.setDisplayName(ChatColor.WHITE + "Lingering Potion of " + effect.getType().getName());
                break;
            case POTION:
                meta.setDisplayName(ChatColor.WHITE + "Potion of " + effect.getType().getName());
                break;
        }
        potion.setItemMeta(meta);
        ((Player)sender).getInventory().addItem(potion);
        return false;
    }
}
