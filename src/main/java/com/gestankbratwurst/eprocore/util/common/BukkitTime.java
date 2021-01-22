package com.gestankbratwurst.eprocore.util.common;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of LaLaLand-CorePlugin and was created at the 22.11.2019
 *
 * LaLaLand-CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class BukkitTime implements Listener {

  private static BukkitTime instance;
  @Getter
  private static long lastTickNanoStart = System.nanoTime();
  @Getter
  private static long lastTickMilliStart = BukkitTime.lastTickNanoStart / 1000000L;

  public static long getTickMillis() {
    return (System.nanoTime() / 1000000L) - BukkitTime.lastTickMilliStart;
  }

  public static long getTickNanos() {
    return System.nanoTime() - BukkitTime.lastTickNanoStart;
  }

  public static boolean isMsElapsed(final long millis) {
    return BukkitTime.getTickMillis() >= millis;
  }

  public static boolean isNsElapsed(final long nanos) {
    return BukkitTime.getTickNanos() >= nanos;
  }

  public static void start(final JavaPlugin plugin) {
    BukkitTime.instance = new BukkitTime();
    Bukkit.getPluginManager().registerEvents(BukkitTime.instance, plugin);
  }

  private BukkitTime() {
  }

  @EventHandler
  public void onTimeStart(final ServerTickStartEvent event) {
    BukkitTime.lastTickNanoStart = System.nanoTime();
    BukkitTime.lastTickMilliStart = BukkitTime.lastTickNanoStart / 1000000L;
  }

}
