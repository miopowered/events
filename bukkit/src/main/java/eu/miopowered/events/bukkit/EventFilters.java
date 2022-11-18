package eu.miopowered.events.bukkit;

import java.util.function.Predicate;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public interface EventFilters {
  static <E extends Cancellable> Predicate<E> ignoreCancelled() {
    return event -> !event.isCancelled();
  }

  static <E extends PlayerMoveEvent> Predicate<E> ignoreSameBlockMovement() {
    return event ->
        event.getFrom().getBlockX() != event.getTo().getBlockX() ||
            event.getFrom().getBlockY() != event.getTo().getBlockY() ||
            event.getFrom().getBlockZ() != event.getTo().getBlockZ() ||
            event.getFrom().getWorld() != event.getTo().getWorld();
  }

  static <E extends PlayerEvent> Predicate<E> withPlayer(
      Predicate<Player> predicate
  ) {
    return event -> predicate.test(event.getPlayer());
  }

  static <E extends EntityEvent> Predicate<E> withEntity(
      Predicate<Entity> predicate
  ) {
    return event -> predicate.test(event.getEntity());
  }

  static <E extends BlockEvent> Predicate<E> withBlock(
      Predicate<Block> predicate
  ) {
    return event -> predicate.test(event.getBlock());
  }

  static <E extends PlayerEvent> Predicate<E> withPlayerNot(
      Predicate<Player> predicate
  ) {
    return event -> !predicate.test(event.getPlayer());
  }

  static <E extends EntityEvent> Predicate<E> withEntityNot(
      Predicate<Entity> predicate
  ) {
    return event -> !predicate.test(event.getEntity());
  }

  static <E extends BlockEvent> Predicate<E> withBlockNot(
      Predicate<Block> predicate
  ) {
    return event -> !predicate.test(event.getBlock());
  }

  static <E extends PlayerEvent> Predicate<E> playerHasPermission(
      String permission
  ) {
    return event -> event.getPlayer().hasPermission(permission);
  }

  static <E extends PlayerEvent> Predicate<E> playerHasPermissionNot(
      String permission
  ) {
    return event -> !event.getPlayer().hasPermission(permission);
  }
}
