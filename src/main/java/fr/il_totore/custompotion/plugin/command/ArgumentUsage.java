package fr.il_totore.custompotion.plugin.command;

import java.util.List;

public interface ArgumentUsage {

	String getUsage();
	String[] getArguments();
	boolean isOptional();
	List<String> getTabArguments(String arg);
}
