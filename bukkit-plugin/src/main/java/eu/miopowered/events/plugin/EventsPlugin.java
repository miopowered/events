package eu.miopowered.events.plugin;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import eu.miopowered.events.api.RegisteredListener;
import eu.miopowered.events.bukkit.EventFilters;
import eu.miopowered.events.bukkit.EventHandlers;
import eu.miopowered.events.bukkit.Events;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsPlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    Events.create(this);

    Events
        .listen(PlayerJoinEvent.class)
        .handleAndRegister(event -> event.getPlayer().sendMessage("Hello!"));

    Events
        .listen(BlockBreakEvent.class)
        .filter(EventFilters.ignoreCancelled())
        .filter(event -> event.getBlock().getType().equals(Material.GRASS_BLOCK))
        .filter(event -> event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)
        )
        .handle(EventHandlers.cancel())
        .handle(event ->
            event.getPlayer().sendMessage("You can't break grass in survival mode!")
        )
        .register();

    Events
        .listen(PlayerMoveEvent.class)
        .filter(EventFilters.ignoreCancelled())
        .filter(EventFilters.ignoreSameBlockMovement())
        .filter(EventFilters.withPlayerNot(Player::isSneaking))
        .handleAndRegister(EventHandlers.cancel());

    Events
        .listen(PlayerGameModeChangeEvent.class)
        .filter(EventFilters.playerHasPermissionNot("eventsplugin.gamemode"))
        .handle(EventHandlers.cancel());

    Events
        .merge(Player.class)
        .bind(PlayerJoinEvent.class, PlayerEvent::getPlayer)
        .bind(
            PlayerGameModeChangeEvent.class,
            PlayerGameModeChangeEvent::getPlayer
        )
        .bind(
            InventoryClickEvent.class,
            entityJumpEvent -> (Player) entityJumpEvent.getWhoClicked()
        )
        .handleAndRegister(player -> player.sendMessage("What are you doing?"));

    Events
        .merge(
            PlayerEvent.class,
            PlayerToggleSneakEvent.class,
            PlayerJumpEvent.class
        )
        .handleAndRegister(event ->
            event.getPlayer().sendMessage("You're sneaking or jumping!")
        );

    RegisteredListener listener = Events
        .listen(PlayerJoinEvent.class)
        .handle(event ->
            event.getPlayer().sendMessage("You shouldn't see this message!")
        )
        .register();

    Bukkit.getScheduler().runTaskLater(this, listener::unregister, 20 * 10);

    this.getLogger()
        .info(
            "The most important thing when fighting and running away is to defend. It is not about beating the enemy in front of them, but to retreat while buying time for their comrades to retreat."
        );
  }
}
