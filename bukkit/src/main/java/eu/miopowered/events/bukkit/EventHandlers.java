package eu.miopowered.events.bukkit;

import java.util.function.Consumer;
import org.bukkit.event.Cancellable;

public interface EventHandlers {

  static <E extends Cancellable> Consumer<E> cancel() {
    return e -> e.setCancelled(true);
  }
}
