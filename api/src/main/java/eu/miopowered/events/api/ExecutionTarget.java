package eu.miopowered.events.api;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ExecutionTarget<E> {
  ExecutionTarget<E> expireIf(Predicate<E> predicate);

  default ExecutionTarget<E> expireAfter(long duration, TimeUnit timeUnit) {
    long expire = System.currentTimeMillis() + timeUnit.toMillis(duration);
    return this.expireIf(event -> System.currentTimeMillis() > expire);
  }

  ExecutionTarget<E> filter(Predicate<E> predicate);

  ExecutionTarget<E> catchException(BiConsumer<E, Throwable> consumer);

  ExecutionTarget<E> handle(Consumer<E> consumer);

  RegisteredListener handleAndRegister(Consumer<E> consumer);

  RegisteredListener register();
}
