package eu.miopowered.events.bukkit.execution.merge;

import eu.miopowered.events.api.RegisteredListener;
import eu.miopowered.events.api.SimpleExecutionTarget;
import eu.miopowered.events.bukkit.Events;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

@Getter
public class BukkitMergedExecutionTarget<E> extends SimpleExecutionTarget<E> {

  private final BukkitMergedExecutionListener<E> listener;
  private final Map<Class<? extends Event>, MergedMapping> mappers;

  public BukkitMergedExecutionTarget(
      Class<E> eventClass,
      EventPriority priority
  ) {
    super(eventClass);
    this.listener = new BukkitMergedExecutionListener<>(this, priority);
    this.mappers = new HashMap<>();
  }

  public <T extends Event> BukkitMergedExecutionTarget<E> bind(
      Class<T> eventClass,
      Function<T, E> function
  ) {
    this.mappers.put(eventClass, new MergedMapping<>(function));
    return this;
  }

  @Override
  public RegisteredListener register() {
    this.listener.register(Events.instance().javaPlugin());
    return this;
  }

  @Override
  public void unregister() {
    this.listener.unregister();
  }
}
