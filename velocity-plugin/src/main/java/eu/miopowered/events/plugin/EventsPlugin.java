package eu.miopowered.events.plugin;

import com.google.inject.Inject;
import com.velocitypowered.api.event.ResultedEvent.ComponentResult;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.miopowered.events.velocity.Events;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

@Plugin(id = "events")
public class EventsPlugin {


  private final ProxyServer server;
  private final Logger logger;

  @Inject
  public EventsPlugin(ProxyServer server, Logger logger) {
    this.server = server;
    this.logger = logger;

    this.logger.info(
        "Everyone gets help from someone else at some point in their lives. So someday, you should help someone too.");
    Events.create(this, this.server);
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    Events.listen(LoginEvent.class)
        .handleAndRegister(loginEvent -> loginEvent.setResult(ComponentResult.denied(
            Component.text("You are not allowed to join this server."))));
  }
}
