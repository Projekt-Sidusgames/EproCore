package com.gestankbratwurst.eprocore.playerdata;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 07.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
@RequiredArgsConstructor
public class PlayerDataListener implements Listener {

  private final EproPlayerManager eproPlayerManager;

  @EventHandler
  public void onLogin(final PlayerLoginEvent event) {
    final Player player = event.getPlayer();
    this.eproPlayerManager.getEproPlayer(player).onLogin(player);
  }

  @EventHandler
  public void onLogout(final PlayerQuitEvent event) {
    final Player player = event.getPlayer();
    this.eproPlayerManager.getEproPlayer(player).onLogout(player);
  }

}
