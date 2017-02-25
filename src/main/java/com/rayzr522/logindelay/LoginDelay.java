package com.rayzr522.logindelay;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Rayzr
 */
public class LoginDelay extends JavaPlugin implements Listener {
    boolean nopeScrewYouTheServerIsntReadyYetStopTryingToJoin = true;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        long delay = (long) (20L * getConfig().getDouble("delay"));
        if (delay < 1)
            delay = 1L;

        new BukkitRunnable() {
            public void run() {
                getLogger().info("Allowing players to connect");
                nopeScrewYouTheServerIsntReadyYetStopTryingToJoin = false;
            }
        }.runTaskLater(this, delay);
    }

    @Override
    public void onDisable() {
        nopeScrewYouTheServerIsntReadyYetStopTryingToJoin = true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerLoginEvent e) {
        if (nopeScrewYouTheServerIsntReadyYetStopTryingToJoin) {
            e.setKickMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("kick-message")));
            e.setResult(Result.KICK_OTHER);
        }
    }

}
