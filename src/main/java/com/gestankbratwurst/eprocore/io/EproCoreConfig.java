package com.gestankbratwurst.eprocore.io;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 22.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class EproCoreConfig {

  public EproCoreConfig(final FileConfiguration fileConfiguration) {
    this.secondsBetweenPlayerDataSaves = fileConfiguration.getInt("PlayerData.SecondsBetweenSaves", 900);
  }

  @Getter
  private final int secondsBetweenPlayerDataSaves;

}
