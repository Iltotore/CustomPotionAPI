package fr.il_totore.custompotion.plugin.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandEffect implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {

        String arg = "help";
        if(args[0] != null) arg = args[0];

        CommandData commandData = CommandData.getArgument(arg);
        if(commandData != null) commandData.getExecutor().onCommand(sender, arg1, arg2, args);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command arg1, String arg2, String[] args) {
        if(args.length == 0) return null;

        CommandData commandData = CommandData.getArgument(args[0]);
        if(commandData == null || args.length <= 1) {
            List<String> tab = new ArrayList<>();
            for(CommandData m : CommandData.values()) {
                if(m.name().toLowerCase().startsWith(args[args.length-1].toLowerCase())) {
                    tab.add(m.name().toLowerCase());
                } else {
                    for(String alias : m.getUsage().getAliases()) {
                        if(alias.startsWith(args[args.length-1].toLowerCase())) tab.add(alias);
                    }
                }

            }
            return tab;
        }
        if(args.length-1 <= commandData.getUsage().getArgumentUsages().length) {
            return commandData.getUsage().getArgumentUsage(args.length-2).getTabArguments(args[args.length-1]);
        }

        return null;
    }
}
