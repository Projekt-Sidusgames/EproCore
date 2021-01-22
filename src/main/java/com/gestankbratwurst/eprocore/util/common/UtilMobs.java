package com.gestankbratwurst.eprocore.util.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of avarioncore and was created at the 08.04.2020
 *
 * avarioncore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilMobs implements Listener {

  private static final String DOMESTICATION_TAG = "DOMESTIC";
  private static final Cache<Integer, Entity> ENTITY_UNLOAD_CACHE = Caffeine.newBuilder()
      .expireAfterWrite(1, TimeUnit.MINUTES)
      .build();

  public static Entity getEntity(final int id, final World world) {
    final net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftWorld) world).getHandle().getEntity(id);
    if (nmsEntity != null) {
      return nmsEntity.getBukkitEntity();
    }
    return UtilMobs.ENTITY_UNLOAD_CACHE.getIfPresent(id);
  }

  public static void init(final JavaPlugin plugin) {
    Bukkit.getPluginManager().registerEvents(new UtilMobs(plugin), plugin);
  }

  public static boolean isDomesticated(final LivingEntity entity) {
    return entity.getScoreboardTags().contains(UtilMobs.DOMESTICATION_TAG);
  }

  private UtilMobs(final JavaPlugin plugin) {

  }


  private final Set<SpawnReason> domesticSpawnReasons = ImmutableSet.of(
      SpawnReason.BREEDING,
      SpawnReason.DISPENSE_EGG,
      SpawnReason.EGG);

  @EventHandler
  public void onSpawn(final CreatureSpawnEvent event) {
    if (this.domesticSpawnReasons.contains(event.getSpawnReason())) {
      event.getEntity().getScoreboardTags().add(UtilMobs.DOMESTICATION_TAG);
    }
  }

  @EventHandler
  public void onBreed(final EntityBreedEvent event) {
    if (event.getBreeder() == null) {
      return;
    }
    event.getMother().getScoreboardTags().add(UtilMobs.DOMESTICATION_TAG);
    event.getFather().getScoreboardTags().add(UtilMobs.DOMESTICATION_TAG);
  }

  @EventHandler
  public void onTame(final EntityTameEvent event) {
    event.getEntity().getScoreboardTags().add(UtilMobs.DOMESTICATION_TAG);
  }

  @EventHandler
  public void onUnload(final ChunkUnloadEvent event) {
    for (final Entity entity : event.getChunk().getEntities()) {
      UtilMobs.ENTITY_UNLOAD_CACHE.put(entity.getEntityId(), entity);
    }
  }

  @EventHandler
  public void onUnload(final WorldUnloadEvent event) {
    for (final Entity entity : event.getWorld().getEntities()) {
      UtilMobs.ENTITY_UNLOAD_CACHE.put(entity.getEntityId(), entity);
    }
  }

}
