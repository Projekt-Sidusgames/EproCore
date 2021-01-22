package com.gestankbratwurst.eprocore.util.common;

import java.nio.ByteBuffer;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of LaLaLand-CorePlugin and was created at the 19.11.2019
 *
 * LaLaLand-CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilVect {

  public static String vecToString(final Vector vector) {
    final ByteBuffer buffer = ByteBuffer.allocate(24);
    buffer.rewind();
    buffer.putDouble(vector.getX());
    buffer.putDouble(vector.getY());
    buffer.putDouble(vector.getZ());
    return new String(buffer.array());
  }

  public static Vector vecFromString(final String vectorKey) {
    final ByteBuffer buffer = ByteBuffer.wrap(vectorKey.getBytes());
    buffer.rewind();
    final double x = buffer.getDouble(0);
    final double y = buffer.getDouble(8);
    final double z = buffer.getDouble(16);
    return new Vector(x, y, z);
  }

  public static String boxToString(final BoundingBox box) {
    return box.getMaxX() + "#" + box.getMaxY() + "#" + box.getMaxZ() + "#" + box.getMinX() + "#" + box.getMinY() + "#" + box.getMinZ();
  }

  public static BoundingBox stringToBox(final String boxString) {
    final String[] split = boxString.split("#");
    final Vector max = new Vector(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    final Vector min = new Vector(Double.parseDouble(split[3]), Double.parseDouble(split[4]), Double.parseDouble(split[5]));
    return BoundingBox.of(max, min);
  }

}
