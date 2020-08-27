package fr.il_totore.custompotion.plugin.command;

import fr.il_totore.custompotion.api.CustomPotionAPI;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum CommandData {

    GIVE(new ArgumentUsages(new ArrayList<>(), new FunctionArgumentUsage(false) {
        @Override
        public List<String> apply() {
            return CustomPotionAPI.getAPI().getRegistry().getCustomTypes().stream()
                    .map(type -> type.getKey().toString())
                    .collect(Collectors.toList());
        }
    }, new SimpleArgumentUsage(new ArrayList<>(), "duration", false), new SimpleArgumentUsage(new ArrayList<>(), "amplifier", true)), new SubCommandGive()),

    POTION(new ArgumentUsages(new ArrayList<>(), new FunctionArgumentUsage(false) {
        @Override
        public List<String> apply() {
            return CustomPotionAPI.getAPI().getRegistry().getCustomTypes().stream()
                    .map(type -> type.getKey().toString())
                    .collect(Collectors.toList());
        }
    }, new SimpleArgumentUsage(new ArrayList<>(), "duration", false), new SimpleArgumentUsage(new ArrayList<>(), "amplifier", true), new SimpleArgumentUsage(Arrays.asList("splash", "normal", "lingering"), "potion type", true)), new SubCommandPotion());

    private ArgumentUsages usage;
    private CommandExecutor executor;

    CommandData(ArgumentUsages usage, CommandExecutor executor) {
        this.usage = usage;
        this.executor = executor;
    }

    public ArgumentUsages getUsage() {
        return usage;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public static CommandData getArgument(String arg) {
        for(CommandData CommandData : values()) {
            if(CommandData.name().equalsIgnoreCase(arg)) return CommandData;
            if(CommandData.getUsage().getAliases().contains(arg.toLowerCase())) return CommandData;
        }
        return null;
    }
}
