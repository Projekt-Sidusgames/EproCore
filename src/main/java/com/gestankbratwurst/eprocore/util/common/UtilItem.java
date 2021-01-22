package com.gestankbratwurst.eprocore.util.common;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of avarioncore and was created at the 07.04.2020
 *
 * avarioncore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilItem implements Listener {

  public static void init(final JavaPlugin plugin) {
    Bukkit.getPluginManager().registerEvents(new UtilItem(), plugin);
  }

  // TODO? One char for every ItemStack in the game?
//  public static char asChar(final ItemStack item) {
//    final NBTItem nbt = new NBTItem(item);
//    if (nbt.hasKey("Model")) {
//      return Model.valueOf(nbt.getString("Model")).getChar();
//    }
//    return 'X';
//  }

  public static String serialize(final ItemStack[] items) {
    try {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

      // Write the size of the inventory
      dataOutput.writeInt(items.length);

      // Save every element in the list
      for (final ItemStack item : items) {
        dataOutput.writeObject(item);
      }

      // Serialize that array
      dataOutput.close();
      return Base64Coder.encodeLines(outputStream.toByteArray());
    } catch (final Exception e) {
      throw new IllegalStateException("Unable to save item stacks.", e);
    }
  }

  public static String serialize(final ItemStack itemStack) {
    final NBTTagCompound tag = new NBTTagCompound();
    CraftItemStack.asNMSCopy(itemStack).save(tag);
    return tag.toString();
  }

  public static ItemStack deserializeItemStack(final String string) {
    if (string == null || string.equals("empty")) {
      return null;
    }
    try {
      final NBTTagCompound comp = MojangsonParser.parse(string);
      final net.minecraft.server.v1_16_R3.ItemStack cis = net.minecraft.server.v1_16_R3.ItemStack.a(comp);
      return CraftItemStack.asBukkitCopy(cis);
    } catch (final CommandSyntaxException ex) {
      ex.printStackTrace();
    }
    return null;
  }


  public static ItemStack[] deserialize(final String data) {
    try {
      final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
      final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
      final ItemStack[] items = new ItemStack[dataInput.readInt()];

      // Read the serialized inventory
      for (int i = 0; i < items.length; i++) {
        items[i] = (ItemStack) dataInput.readObject();
      }

      dataInput.close();
      return items;
    } catch (final ClassNotFoundException | IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static boolean isEnachantable(final ItemStack item) {
    if (!item.getEnchantments().isEmpty()) {
      return false;
    }
    final PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
    return !container.has(NameSpaceFactory.provide("ENCHANTBLOCK"), PersistentDataType.INTEGER);
  }

  public static boolean canReceiveAnvil(final ItemStack item) {
    final PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
    return !container.has(NameSpaceFactory.provide("BLOCKANVILRECEIVER"), PersistentDataType.INTEGER);
  }

  public static boolean canProvideAnvil(final ItemStack item) {
    final PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
    return !container.has(NameSpaceFactory.provide("BLOCKANVILPROVIDER"), PersistentDataType.INTEGER);
  }

  private UtilItem() {

  }

  @EventHandler
  public void onEnchant(final PrepareItemEnchantEvent event) {
    if (UtilItem.isEnachantable(event.getItem())) {
      return;
    }
    event.setCancelled(true);
  }

  @EventHandler
  public void onAnvil(final PrepareAnvilEvent event) {
    final ItemStack left = event.getInventory().getItem(0);
    final ItemStack right = event.getInventory().getItem(1);
    if (left == null || right == null || left.getType() == Material.AIR || right.getType() == Material.AIR) {
      return;
    }
    if (!UtilItem.canReceiveAnvil(left) || !UtilItem.canProvideAnvil(right)) {
      event.setResult(null);
    }
  }

  public static ItemStack produceHead(final GameProfile gameProfile) {
    final ItemStack newHead = new ItemStack(Material.PLAYER_HEAD);
    final SkullMeta headMeta = (SkullMeta) newHead.getItemMeta();
    final Field profileField;

    try {
      profileField = headMeta.getClass().getDeclaredField("profile");
      profileField.setAccessible(true);
      profileField.set(headMeta, gameProfile);
    } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }

    newHead.setItemMeta(headMeta);
    return newHead;
  }

}
