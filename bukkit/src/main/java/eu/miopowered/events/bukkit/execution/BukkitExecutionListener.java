package eu.miopowered.events.bukkit.execution;

import eu.miopowered.events.api.SimpleExecutionTarget;
import eu.miopowered.events.bukkit.utility.BukkitReflections;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class BukkitExecutionListener<E extends Event>
    implements EventExecutor, Listener, ExecutionListener {

  private final SimpleExecutionTarget<E> executionTarget;
  private final EventPriority eventPriority;
  private final AtomicBoolean active = new AtomicBoolean(true);

  @Override
  public void execute(@NotNull Listener listener, @NotNull Event event)
      throws EventException {
    if (!this.executionTarget.eventClass().equals(event.getClass())) {
      return;
    }
    if (!this.active.get()) {
      event.getHandlers().unregister(listener);
      return;
    }

    E eventInstance = this.executionTarget.eventClass().cast(event);

    if (
        this.executionTarget.expirations()
            .stream()
            .anyMatch(expiration -> expiration.test(eventInstance))
    ) {
      event.getHandlers().unregister(listener);
      this.active.set(false);
      return;
    }

    try {
      if (
          !this.executionTarget.filters()
              .stream()
              .allMatch(predicate -> predicate.test(eventInstance))
      ) {
        return;
      }
      this.executionTarget.handlers()
          .forEach(consumer -> consumer.accept(eventInstance));
    } catch (Throwable throwable) {
      this.executionTarget.exceptionConsumer().accept(eventInstance, throwable);
    }
  }

  @Override
  public void unregister() {
    if (!this.active.getAndSet(false)) {
      return;
    }

    BukkitReflections.unregisterListener(
        this.executionTarget.eventClass(),
        this
    );
  }

  @Override
  public void register(JavaPlugin javaPlugin) {
    javaPlugin
        .getServer()
        .getPluginManager()
        .registerEvent(
            this.executionTarget.eventClass(),
            this,
            this.eventPriority,
            this,
            javaPlugin,
            false
        );
  }
}
