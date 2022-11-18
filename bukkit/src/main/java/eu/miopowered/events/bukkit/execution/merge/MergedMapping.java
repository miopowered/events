package eu.miopowered.events.bukkit.execution.merge;

import java.util.function.Function;

public record MergedMapping<T, E>(Function<T, E> function) {
  
}
