package eu.miopowered.events.bukkit.execution;

import eu.miopowered.events.api.RegisteredListener;
import eu.miopowered.events.api.SimpleExecutionTarget;
import eu.miopowered.events.bukkit.Events;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

public class BukkitExecutionTarget<E extends Event>
    extends SimpleExecutionTarget<E> {

  private final BukkitExecutionListener<E> listener;

  public BukkitExecutionTarget(
      Class<E> eventClass,
      EventPriority eventPriority
  ) {
    super(eventClass);
    this.listener = new BukkitExecutionListener<>(this, eventPriority);
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
