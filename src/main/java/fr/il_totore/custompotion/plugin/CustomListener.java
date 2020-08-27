package fr.il_totore.custompotion.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public abstract class CustomListener<T extends Event> implements EventExecutor, Listener {
    public abstract void run(T event);

    @Override
    @SuppressWarnings("unchecked")
    public void execute(Listener listener, Event event) {
        run((T) event);

    }
}
