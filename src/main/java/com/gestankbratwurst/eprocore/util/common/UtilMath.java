package com.gestankbratwurst.eprocore.util.common;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of LaLaLand-CorePlugin and was created at the 16.11.2019
 *
 * LaLaLand-CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class UtilMath {

  public static double cut(double value, final int decimalPoints) {
    final int decades = (int) Math.pow(10, decimalPoints);
    value = ((double) ((int) (value * decades))) / 10;
    return value;
  }

  public static int round(final double value) {
    return (int) (value + 0.5);
  }

  public static String getPercentageBar(final double current, final double max, final int size, final String segment) {
    final StringBuilder builder = new StringBuilder();
    int lows = (int) (size * ((1D / max) * current));
    int highs = size - lows;
    builder.append("§a");
    while (lows > 0) {
      builder.append(segment);
      lows--;
    }
    builder.append("§c");
    while (highs > 0) {
      builder.append(segment);
      highs--;
    }
    return builder.toString();
  }

  public static Double parseDouble(final String value) {
    try {
      return Double.parseDouble(value);
    } catch (final NumberFormatException exception) {
      return null;
    }
  }

  public static Integer parseInt(final String value) {
    try {
      return Integer.parseInt(value);
    } catch (final NumberFormatException exception) {
      return null;
    }
  }

  public static Long parseLong(final String value) {
    try {
      return Long.parseLong(value);
    } catch (final NumberFormatException exception) {
      return null;
    }
  }

}
