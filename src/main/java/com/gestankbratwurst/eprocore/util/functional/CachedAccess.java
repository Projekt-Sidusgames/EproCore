package com.gestankbratwurst.eprocore.util.functional;

import java.time.Duration;
import java.util.function.Supplier;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of PSSCore and was created at the 08.11.2020
 *
 * PSSCore can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class CachedAccess<T> {

  public CachedAccess(final Duration cacheTime, final Supplier<T> refreshSupplier) {
    this.refreshSupplier = refreshSupplier;
    this.cacheTime = cacheTime;
  }

  private final Duration cacheTime;
  private final Supplier<T> refreshSupplier;
  private long lastRefresh;
  private T value;

  public T getValue() {
    final long now = System.currentTimeMillis();
    if (now - this.lastRefresh >= this.cacheTime.toMillis()) {
      this.value = this.refreshSupplier.get();
      this.lastRefresh = now;
    }
    return this.value;
  }

}
