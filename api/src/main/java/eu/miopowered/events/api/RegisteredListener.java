package eu.miopowered.events.api;

public interface RegisteredListener {
  void unregister();

  default void close() {
    this.unregister();
  }
}
