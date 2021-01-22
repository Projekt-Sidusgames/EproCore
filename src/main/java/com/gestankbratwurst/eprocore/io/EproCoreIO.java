package com.gestankbratwurst.eprocore.io;

import com.gestankbratwurst.eprocore.playerdata.EproPlayer;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 08.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public interface EproCoreIO {

  void savePlayerData(EproPlayer eproPlayer);

  EproPlayer loadPlayerData(UUID playerID);

  Collection<UUID> getRegisteredPlayerIDs();

  default List<EproPlayer> loadAllPlayerData() {
    return this.getRegisteredPlayerIDs().stream().map(this::loadPlayerData).collect(Collectors.toList());
  }

  default void saveAllPlayerData(final Collection<EproPlayer> players) {
    players.forEach(this::savePlayerData);
  }

}
