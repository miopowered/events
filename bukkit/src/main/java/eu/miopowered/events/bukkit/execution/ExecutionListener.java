package eu.miopowered.events.bukkit.execution;

import org.bukkit.plugin.java.JavaPlugin;

public interface ExecutionListener {
  void unregister();

  void register(JavaPlugin javaPlugin);
}
