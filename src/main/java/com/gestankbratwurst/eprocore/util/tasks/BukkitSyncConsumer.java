package com.gestankbratwurst.eprocore.util.tasks;

import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.function.Consumer;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of CorePlugin and was created at the 03.01.2020
 *
 * CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class BukkitSyncConsumer<T> implements Runnable {

  private final ArrayDeque<Pair<ItemStack, Consumer<ItemStack>>> itemActions = Queues.newArrayDeque();

  public void addAction(final ItemStack item, final Consumer<ItemStack> action) {
    this.itemActions.add(Pair.of(item, action));
  }

  public void invokeOne() {
    if (!this.itemActions.isEmpty()) {
      final Pair<ItemStack, Consumer<ItemStack>> pair = this.itemActions.poll();
      pair.getRight().accept(pair.getLeft());
    }
  }

  public BukkitSyncConsumer(final T object, final Consumer<T> action, final JavaPlugin plugin) {
    this.plugin = plugin;
    this.sync = new ConsumingBukkitSync<>(object, action);
  }

  private final JavaPlugin plugin;
  private final ConsumingBukkitSync<T> sync;

  @Override

  public void run() {
    this.sync.runTask(this.plugin);
  }

}
