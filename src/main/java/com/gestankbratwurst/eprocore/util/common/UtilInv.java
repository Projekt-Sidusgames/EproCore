package com.gestankbratwurst.eprocore.util.common;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PSSCore and was created at the 10.11.2020
 *
 * PSSCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilInv {

  public static ItemStack[] getVerticallyFlippedContent(final Inventory inventory) {
    if (inventory.getType() != InventoryType.CHEST) {
      throw new UnsupportedOperationException("Currently only supports chest inventories.");
    }
    final ItemStack[] baseContent = inventory.getContents();
    final ItemStack[] flippedContent = new ItemStack[baseContent.length];

    final int rows = baseContent.length % 9;

    for (int row = 0; row < rows; row++) {
      for (int x = 0; x < 9; x++) {
        flippedContent[row * 9 + (8 - x)] = baseContent[row * 9 + x];
      }
    }

    return flippedContent;
  }

  public static int getVerticallyFlippedIndex(final int index) {
    final int x = index % 9;
    final int row = index / 9;
    return row * 9 + (8 - x);
  }

  public static int remove(final Iterable<ItemStack> content, final ItemStack item, final int amount) {
    int left = amount;
    for (final ItemStack invItem : content) {
      if (invItem != null && invItem.isSimilar(item)) {
        final int size = invItem.getAmount();
        if (size > left) {
          invItem.setAmount(size - left);
          left = 0;
          break;
        } else {
          left -= size;
          invItem.setAmount(0);
        }
      }
    }
    return amount - left;
  }

  public static boolean contains(final Iterable<ItemStack> content, final ItemStack item, final int amount) {
    int left = amount;
    for (final ItemStack invItem : content) {
      if (invItem != null && invItem.isSimilar(item)) {
        left -= invItem.getAmount();
        if (left <= 0) {
          return true;
        }
      }
    }
    return false;
  }

}
