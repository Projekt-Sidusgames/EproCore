package com.gestankbratwurst.eprocore.util.common.sub;

import java.nio.ByteBuffer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.bukkit.block.Block;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of avarioncore and was created at the 26.03.2020
 *
 * avarioncore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
@AllArgsConstructor
@EqualsAndHashCode
public class BlockPosition {

  public BlockPosition(final Block block) {
    this.x = block.getX();
    this.y = block.getY();
    this.z = block.getZ();
  }

  public static BlockPosition fromBytes(final byte[] bytes) {
    final ByteBuffer buffer = ByteBuffer.wrap(bytes);
    return new BlockPosition(buffer.getInt(), buffer.getInt(), buffer.getInt());
  }

  public final int x;
  public final int y;
  public final int z;

  @Override
  public String toString() {
    return this.x + " | " + this.y + " | " + this.z;
  }

  public byte[] toBytes() {
    final ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 3);

    buffer.putInt(this.x);
    buffer.putInt(this.y);
    buffer.putInt(this.z);

    return buffer.array();
  }

}
