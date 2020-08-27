package fr.il_totore.custompotion.plugin.command;

import fr.il_totore.custompotion.api.*;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SubCommandGive implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
        if(!(sender instanceof Player)) return false;
        CustomPotionAPI api = CustomPotionAPI.getAPI();

        String[] rawKey = args[1].split(":");
        NamespacedKey key = new NamespacedKey(rawKey[0], rawKey[1]);
        CustomEffectType type = api.getRegistry().getType(key);
        long duration = Long.parseLong(args[2])*20;

        if(type == null){
            sender.sendMessage(ChatColor.RED + "Unknown effect: " + args[1]);
        }

        CustomEffectData data = api.getData((Player)sender, false);
        CustomPotionEffect effect = new CustomPotionEffect(type, duration, (byte)0);
        data.addPotionEffect(effect, true);
        return false;
    }

}