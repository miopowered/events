package eu.miopowered.events.bukkit.utility;

import java.lang.reflect.Method;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class BukkitReflections {

  public static void unregisterListener(
      Class<? extends Event> eventClass,
      Listener listener
  ) {
    try {
      Method getHandlerListMethod = eventClass.getMethod("getHandlerList");
      HandlerList handlerList = (HandlerList) getHandlerListMethod.invoke(null);
      handlerList.unregister(listener);
    } catch (Throwable ignored) {
    }
  }
}
