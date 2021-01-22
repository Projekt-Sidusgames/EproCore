package com.gestankbratwurst.eprocore.io;

import com.gestankbratwurst.eprocore.EproCore;
import com.gestankbratwurst.eprocore.playerdata.EproPlayer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of EproCore and was created at the 22.01.2021
 *
 * EproCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class EproFlatFileIO implements EproCoreIO {

  private final EproCore plugin;
  private final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
  private final File pluginFolder;
  private final File playerDataFolder;
  private final EproCoreConfig loadedConfig;

  public EproFlatFileIO(final EproCore plugin) {
    this.plugin = plugin;
    this.pluginFolder = plugin.getDataFolder();
    this.playerDataFolder = new File(this.pluginFolder + File.separator + "playerData");
    this.playerDataFolder.mkdirs();
    this.loadedConfig = this.loadConfig();
  }

  private File getPlayerFile(final UUID playerID) {
    return new File(this.playerDataFolder, playerID.toString());
  }

  @Override
  public void savePlayerData(final EproPlayer eproPlayer) {
    final File playerFile = this.getPlayerFile(eproPlayer.getPlayerID());
    final String json = this.gson.toJson(eproPlayer);
    try {
      Files.writeString(playerFile.toPath(), json);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public EproPlayer loadPlayerData(final UUID playerID) {
    final File playerFile = this.getPlayerFile(playerID);
    try {
      final String json = Files.readString(playerFile.toPath());
      return this.gson.fromJson(json, EproPlayer.class);
    } catch (final IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Collection<UUID> getRegisteredPlayerIDs() {
    return new ArrayList<>();
  }

  @Override
  public EproCoreConfig loadConfig() {
    if (this.loadedConfig != null) {
      return this.loadedConfig;
    }
    final File configFile = new File(this.pluginFolder, "configuration.yml");
    if (!configFile.exists()) {
      this.plugin.saveResource("configuration.yml", true);
    }
    final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
    return new EproCoreConfig(fileConfiguration);
  }
}
