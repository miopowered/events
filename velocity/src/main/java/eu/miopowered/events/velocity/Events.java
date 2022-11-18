package eu.miopowered.events.velocity;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.miopowered.events.api.ExecutionTarget;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Events {

  @Getter
  private static Events instance;

  public static Events create(Object plugin, ProxyServer server) {
    return instance = new Events(plugin, server);
  }

  public static <E> ExecutionTarget<E> listen(
      Class<E> eventClass,
      PostOrder order
  ) {
    return new VelocityExecutionTarget<>(eventClass, order);
  }

  public static <E> ExecutionTarget<E> listen(Class<E> eventClass) {
    return listen(eventClass, PostOrder.NORMAL);
  }

  private final Object plugin;
  private final ProxyServer proxyServer;
}
