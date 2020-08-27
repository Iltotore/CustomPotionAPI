package fr.il_totore.custompotion.plugin.command;

import java.util.List;

public class ArgumentUsages {

	private ArgumentUsage[] argumentUsages;
	private List<String> aliases;
	
	public ArgumentUsages(List<String> aliases, ArgumentUsage...argumentUsages) {
		this.argumentUsages = argumentUsages;
		this.aliases = aliases;
	}

	public ArgumentUsage[] getArgumentUsages() {
		return argumentUsages;
	}
	
	public ArgumentUsage getArgumentUsage(int i) {
		return argumentUsages[i];
	}
	
	public List<String> getAliases() {
		return aliases;
	}

	public String getUsage() {
		String txt = "";
		for(ArgumentUsage arg : argumentUsages) {
			txt = txt + arg.getUsage() + " ";
		}
		return txt;
	}
}
