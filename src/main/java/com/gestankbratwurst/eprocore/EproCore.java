package com.gestankbratwurst.eprocore;

import co.aikar.commands.PaperCommandManager;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.gestankbratwurst.eprocore.io.EproCoreIO;
import com.gestankbratwurst.eprocore.playerdata.PlayerDataManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 07.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public final class EproCore extends JavaPlugin {

  @Getter
  private PaperCommandManager paperCommandManager;
  @Getter
  private PlayerDataManager playerDataManager;
  @Getter
  private EproCoreIO eproCoreIO;
  @Getter
  private ProtocolManager protocolManager;

  @Override
  public void onEnable() {
    // TODO: eproCoreIO
    this.protocolManager = ProtocolLibrary.getProtocolManager();
    this.paperCommandManager = new PaperCommandManager(this);
    this.playerDataManager = new PlayerDataManager(this);

    this.playerDataManager.onEnable();

  }

  @Override
  public void onDisable() {
    this.playerDataManager.onDisable();
  }
}
