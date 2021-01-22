package com.gestankbratwurst.eprocore.playerdata;

import com.destroystokyo.paper.profile.PlayerProfile;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 07.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class EproPlayer {

  public static EproPlayer of(final UUID playerID) {
    return EproPlayerManager.getInstance().getEproPlayer(playerID);
  }

  public static EproPlayer of(final Player player) {
    return EproPlayerManager.getInstance().getEproPlayer(player);
  }

  @Getter
  private final UUID playerID;
  @Getter
  private PlayerProfile playerProfile;

  public Optional<Player> getOnlinePlayer() {
    return Optional.ofNullable(Bukkit.getPlayer(this.playerID));
  }

  public PlayerProfile getLastSeenPlayerProfile() {
    return this.getOnlinePlayer().map(Player::getPlayerProfile).orElse(this.playerProfile);
  }

  public EproPlayer(final UUID playerID) {
    this.playerID = playerID;
  }

  protected void onLogin(final Player player) {
    this.playerProfile = player.getPlayerProfile();
  }

  protected void onLogout(final Player player) {
    this.playerProfile = player.getPlayerProfile();
  }
}
