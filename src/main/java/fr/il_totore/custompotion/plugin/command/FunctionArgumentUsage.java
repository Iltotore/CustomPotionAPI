package fr.il_totore.custompotion.plugin.command;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionArgumentUsage implements ArgumentUsage{

	private boolean optional;
	
	public FunctionArgumentUsage(boolean optional) {
		this.optional = optional;
	}
	
	@Override
	public String getUsage() {
		String[] args = getArguments();
		String usage = "";
		usage = "<" + args[0];
		for(int i = 1; i < args.length; i++) {
			usage = usage + "|" + args[i];
		}
		usage = usage + "]";
		return usage;
	}


	@Override
	public boolean isOptional() {
		return optional;
	}

	@Override
	public List<String> getTabArguments(String tab) {
		List<String> completion = new ArrayList<>();
		for(String arg : apply()) {
			System.out.println(arg + "?=" + tab);
			if(arg.toLowerCase().startsWith(tab.toLowerCase())) completion.add(arg);
		}
		return completion;
	}

	@Override
	public String[] getArguments() {
		return new String[0];
	}

	public abstract List<String> apply();
}
