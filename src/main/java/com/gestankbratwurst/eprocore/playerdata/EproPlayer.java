package com.gestankbratwurst.eprocore.playerdata;

import java.util.UUID;
import lombok.Getter;

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

  @Getter
  private final UUID playerID;

  public EproPlayer(final UUID playerID) {
    this.playerID = playerID;
  }

}
