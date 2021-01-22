package com.gestankbratwurst.eprocore.util;


import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/*******************************************************
 * Copyright (C) Gestankbratwurst suotokka@gmail.com
 *
 * This file is part of LaLaLand-CorePlugin and was created at the 16.11.2019
 *
 * LaLaLand-CorePlugin can not be copied and/or distributed without the express
 * permission of the owner.
 *
 */
public class Msg {

  public static final ChatColor MODULE_COLOR = ChatColor.BLUE;
  public static final ChatColor ERROR_COLOR = ChatColor.DARK_RED;
  public static final ChatColor MESSAGE_COLOR = ChatColor.GRAY;
  public static final ChatColor ELEMENT_COLOR = ChatColor.YELLOW;

  /**
   * Used to send a message to any player.
   *
   * @param player  the player
   * @param module  the prefix
   * @param message the message
   */
  public static void send(final Player player, final String module, final String message) {
    player.sendMessage(Msg.MODULE_COLOR + module + "> " + Msg.MESSAGE_COLOR + message);
  }

  /**
   * Used to format elements.
   *
   * @param input the input element.
   * @return a formated element.
   */
  public static String elem(final String input) {
    return Msg.ELEMENT_COLOR + input + Msg.MESSAGE_COLOR;
  }

  /**
   * Used to send error message to player.
   *
   * @param player  the player
   * @param module  the prefix
   * @param message the message
   */
  public static void error(final Player player, final String module, final String message) {
    player.sendMessage(Msg.ERROR_COLOR + module + "> " + Msg.MESSAGE_COLOR + message);
  }

  public static void sendComponents(final Player player, final String module, final BaseComponent... components) {
    final TextComponent parent = new TextComponent(module + "> ");
    parent.setColor(Msg.MODULE_COLOR);
    for (final BaseComponent component : components) {
      parent.addExtra(component);
    }
    player.sendMessage(parent);
  }

  public static class Pack {

    public Pack(final String moduleName, final String message) {
      this.moduleName = moduleName;
      this.message = message;
    }

    @Getter
    private final String moduleName;
    @Getter
    private final String message;

  }

}