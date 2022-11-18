package eu.miopowered.events.api;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import lombok.Getter;

@Getter
public abstract class SimpleExecutionTarget<E>
    implements ExecutionTarget<E>, RegisteredListener {

  private final Class<E> eventClass;
  private final List<Predicate<E>> filters, expirations;
  private final List<Consumer<E>> handlers;
  private BiConsumer<E, Throwable> exceptionConsumer;

  public SimpleExecutionTarget(Class<E> eventClass) {
    this.eventClass = eventClass;
    this.filters = new ArrayList<>();
    this.expirations = new ArrayList<>();
    this.handlers = new ArrayList<>();
    this.exceptionConsumer =
        (e, throwable) -> {
          throw new RuntimeException(throwable);
        };
  }

  @Override
  public ExecutionTarget<E> expireIf(Predicate<E> predicate) {
    this.expirations.add(predicate);
    return this;
  }

  @Override
  public ExecutionTarget<E> filter(Predicate<E> predicate) {
    this.filters.add(predicate);
    return this;
  }

  @Override
  public ExecutionTarget<E> catchException(BiConsumer<E, Throwable> consumer) {
    this.exceptionConsumer = consumer;
    return this;
  }

  @Override
  public ExecutionTarget<E> handle(Consumer<E> consumer) {
    this.handlers.add(consumer);
    return this;
  }

  @Override
  public RegisteredListener handleAndRegister(Consumer<E> consumer) {
    this.handle(consumer);
    this.register();
    return this;
  }
}
