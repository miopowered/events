package eu.miopowered.events.velocity;

import com.velocitypowered.api.event.EventHandler;
import com.velocitypowered.api.event.PostOrder;
import eu.miopowered.events.api.RegisteredListener;
import eu.miopowered.events.api.SimpleExecutionTarget;
import java.util.concurrent.atomic.AtomicBoolean;

public class VelocityExecutionTarget<E> extends SimpleExecutionTarget<E> implements
    EventHandler<E> {

  private final PostOrder postOrder;
  private final AtomicBoolean active = new AtomicBoolean(true);

  public VelocityExecutionTarget(Class<E> eventClass, PostOrder postOrder) {
    super(eventClass);

    this.postOrder = postOrder;
  }

  @Override
  public RegisteredListener register() {
    Events.instance().proxyServer().getEventManager().register(
        Events.instance().plugin(),
        this.eventClass(),
        this.postOrder,
        this
    );
    return this;
  }

  @Override
  public void unregister() {
    Events.instance().proxyServer().getEventManager().unregister(
        Events.instance().plugin(),
        this
    );
  }

  @Override
  public void execute(E event) {
    if (!this.active.get()) {
      return;
    }
    if (this.expirations().stream()
        .anyMatch(expiration -> expiration.test(event))) {
      this.unregister();
      this.active.set(false);
      return;
    }

    if (!this.filters().stream().allMatch(predicate -> predicate.test(event))) {
      return;
    }

    try {
      if (!this.filters().stream()
          .allMatch(predicate -> predicate.test(event))) {
        return;
      }
      this.handlers().forEach(consumer -> consumer.accept(event));
    } catch (Throwable throwable) {
      this.exceptionConsumer().accept(event, throwable);
    }
  }
}
