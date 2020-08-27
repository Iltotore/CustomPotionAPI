package fr.il_totore.custompotion.plugin.command;

import java.util.ArrayList;
import java.util.List;

public class SimpleArgumentUsage implements ArgumentUsage{

	private String usage;
	private String[] args;
	private boolean optional;
	
	public SimpleArgumentUsage(String...args) {
		this.args = args;
		this.optional = false;
		this.usage = "[" + args[0];
		for(int i = 1; i < args.length; i++) {
			this.usage = usage + "|" + args[i];
		}
		this.usage = usage + "]";
	}
	
	public SimpleArgumentUsage(String text, List<String> args) {
		this(args, false);
	}
	
	public SimpleArgumentUsage(List<String> args, boolean optional) {
		this.args = args.toArray(new String[] {});
		this.optional = optional;
		this.usage = optional ? "[" : "<" + this.args[0];
		for(int i = 1; i < this.args.length; i++) {
			this.usage = usage + "|" + this.args[i];
		}
		this.usage += optional ? "]" : ">";
	}
	
	public SimpleArgumentUsage(List<String> args, String text, boolean optional) {
		this.args = args.toArray(new String[] {});
		this.optional = optional;
		if(optional) {
			this.usage = "[" + text + "]";
		} else {
			this.usage = "<" + text + ">";
		}
	}
	
	public String getUsage() {
		return usage;
	}
	
	public String[] getArguments() {
		return args;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	public List<String> getTabArguments(String tab) {
		List<String> completion = new ArrayList<>();
		for(String arg : args) {
			if(arg.startsWith(tab)) completion.add(arg);
		}
		return completion;
	}
	
	public static SimpleArgumentUsage BOOLEAN = new SimpleArgumentUsage("true", "false");
}
