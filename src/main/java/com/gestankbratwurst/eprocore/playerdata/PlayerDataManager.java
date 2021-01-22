package com.gestankbratwurst.eprocore.playerdata;

import com.gestankbratwurst.eprocore.EproCore;
import com.gestankbratwurst.eprocore.io.EproCoreIO;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 07.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class PlayerDataManager implements Iterable<EproPlayer> {

  private final EproCoreIO eproCoreIO;
  private final HashMap<UUID, EproPlayer> eproPlayerCache;
  private final EproCore plugin;
  private final BukkitScheduler scheduler;

  public PlayerDataManager(final EproCore plugin) {
    this.eproPlayerCache = new HashMap<>();
    this.plugin = plugin;
    this.eproCoreIO = plugin.getEproCoreIO();
    this.scheduler = Bukkit.getScheduler();
  }

  public void onEnable() {
    final List<EproPlayer> loadedEproPlayers = this.eproCoreIO.loadAllPlayerData();
    for (final EproPlayer eproPlayer : loadedEproPlayers) {
      this.eproPlayerCache.put(eproPlayer.getPlayerID(), eproPlayer);
    }
  }

  public void onDisable() {
    this.eproCoreIO.saveAllPlayerData(this.eproPlayerCache.values());
  }

  public EproPlayer getEproPlayer(final UUID playerID) {
    return this.eproPlayerCache.get(playerID);
  }

  public EproPlayer getEproPlayer(final Player player) {
    return this.getEproPlayer(player.getUniqueId());
  }

  public Future<EproPlayer> getEproPlayerAsync(final UUID playerID) {
    return this.scheduler.callSyncMethod(this.plugin, () -> this.getEproPlayer(playerID));
  }

  public void computeSyncForEproPlayer(final UUID playerID, final Consumer<EproPlayer> action) {
    this.scheduler.runTask(this.plugin, () -> action.accept(this.getEproPlayer(playerID)));
  }

  @Override
  @NotNull
  public Iterator<EproPlayer> iterator() {
    return this.eproPlayerCache.values().iterator();
  }

  @Override
  public void forEach(final Consumer<? super EproPlayer> action) {
    this.eproPlayerCache.values().forEach(action);
  }

  @Override
  public Spliterator<EproPlayer> spliterator() {
    return this.eproPlayerCache.values().spliterator();
  }
}