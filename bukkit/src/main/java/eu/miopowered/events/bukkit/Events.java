package eu.miopowered.events.bukkit;

import eu.miopowered.events.api.ExecutionTarget;
import eu.miopowered.events.bukkit.execution.BukkitExecutionTarget;
import eu.miopowered.events.bukkit.execution.merge.BukkitMergedExecutionTarget;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Events {

  @Getter
  private static Events instance;

  public static Events create(JavaPlugin javaPlugin) {
    return instance = new Events(javaPlugin);
  }

  public static <E extends Event> ExecutionTarget<E> listen(
      Class<E> eventClass,
      EventPriority priority
  ) {
    return new BukkitExecutionTarget<>(eventClass, priority);
  }

  public static <E extends Event> ExecutionTarget<E> listen(
      Class<E> eventClass
  ) {
    return listen(eventClass, EventPriority.NORMAL);
  }

  public static <E> BukkitMergedExecutionTarget<E> merge(
      Class<E> clazz,
      EventPriority priority
  ) {
    return new BukkitMergedExecutionTarget<>(clazz, priority);
  }

  public static <E> BukkitMergedExecutionTarget<E> merge(Class<E> clazz) {
    return merge(clazz, EventPriority.NORMAL);
  }

  @SafeVarargs
  public static <E extends Event> BukkitMergedExecutionTarget<E> merge(
      Class<E> superClass,
      Class<? extends E>... eventClasses
  ) {
    return merge(superClass, EventPriority.NORMAL, eventClasses);
  }

  @SafeVarargs
  public static <E extends Event> BukkitMergedExecutionTarget<E> merge(
      Class<E> superClass,
      EventPriority priority,
      Class<? extends E>... eventClasses
  ) {
    BukkitMergedExecutionTarget<E> target = merge(superClass, priority);
    for (Class<? extends E> event : eventClasses) {
      target.bind(event, e -> e);
    }
    return target;
  }

  @Getter
  private final JavaPlugin javaPlugin;

  private Events(JavaPlugin javaPlugin) {
    this.javaPlugin = javaPlugin;
  }
}
