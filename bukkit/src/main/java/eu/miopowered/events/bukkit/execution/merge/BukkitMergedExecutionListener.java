package eu.miopowered.events.bukkit.execution.merge;

import eu.miopowered.events.bukkit.execution.ExecutionListener;
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
public class BukkitMergedExecutionListener<E> implements EventExecutor, Listener, ExecutionListener {

  private final BukkitMergedExecutionTarget<E> target;
  private final EventPriority eventPriority;
  private final AtomicBoolean active = new AtomicBoolean(true);

  @Override
  public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
    if (!this.target.mappers().containsKey(event.getClass())) {
      return;
    }
    if (!this.active.get()) {
      event.getHandlers().unregister(listener);
      return;
    }

    MergedMapping<? super Event, E> wrapper = this.target.mappers().get(event.getClass());
    E instance = wrapper.function().apply(event);
    if (this.target.expirations().stream()
        .anyMatch(expiration -> expiration.test(instance))) {
      event.getHandlers().unregister(listener);
      this.active.set(false);
      return;
    }

    try {
      if (!this.target.filters().stream()
          .allMatch(predicate -> predicate.test(instance))) {
        return;
      }
      this.target.handlers().forEach(consumer -> consumer.accept(instance));
    } catch (Throwable throwable) {
      this.target.exceptionConsumer().accept(instance, throwable);
    }
  }

  @Override
  public void unregister() {
    if (!this.active.getAndSet(false)) {
      return;
    }

    this.target.mappers().keySet()
        .forEach(aClass -> BukkitReflections.unregisterListener(aClass, this));
  }

  @Override
  public void register(JavaPlugin javaPlugin) {
    this.target.mappers().keySet().forEach(aClass -> {
      javaPlugin.getServer().getPluginManager().registerEvent(
          aClass,
          this,
          this.eventPriority,
          this,
          javaPlugin,
          false
      );
    });
  }
}
