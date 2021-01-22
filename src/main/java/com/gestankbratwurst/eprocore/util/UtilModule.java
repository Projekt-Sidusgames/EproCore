package com.gestankbratwurst.eprocore.util;

import com.gestankbratwurst.eprocore.EproCore;
import com.gestankbratwurst.eprocore.util.actionbar.ActionBarManager;
import com.gestankbratwurst.eprocore.util.common.BukkitTime;
import com.gestankbratwurst.eprocore.util.common.NameSpaceFactory;
import com.gestankbratwurst.eprocore.util.common.UtilBlock;
import com.gestankbratwurst.eprocore.util.common.UtilItem;
import com.gestankbratwurst.eprocore.util.common.UtilMobs;
import com.gestankbratwurst.eprocore.util.common.UtilPlayer;
import com.gestankbratwurst.eprocore.util.items.display.ItemDisplayCompiler;
import lombok.Getter;
import net.crytec.inventoryapi.InventoryAPI;
import net.crytec.libs.protocol.ProtocolAPI;
import net.crytec.libs.protocol.holograms.impl.HologramManager;
import net.crytec.libs.protocol.holograms.impl.infobar.InfoBar;
import net.crytec.libs.protocol.holograms.infobars.InfoBarManager;
import net.crytec.libs.protocol.npc.NpcAPI;
import net.crytec.libs.protocol.skinclient.PlayerSkinManager;
import net.crytec.libs.protocol.tablist.TabListManager;
import net.crytec.libs.protocol.tablist.implementation.EmptyTablist;

public class UtilModule {

  @Getter
  private HologramManager hologramManager;
  @Getter
  private ActionBarManager actionBarManager;
  @Getter
  private InfoBarManager infoBarManager;
  @Getter
  private ProtocolAPI protocolAPI;
  @Getter
  private NpcAPI npcAPI;
  @Getter
  private TabListManager tabListManager;
  @Getter
  private PlayerSkinManager playerSkinManager;
  @Getter
  private ItemDisplayCompiler displayCompiler;

  public void enable(final EproCore plugin) {
    BukkitTime.start(plugin);
    NameSpaceFactory.init(plugin);
    UtilPlayer.init(plugin);
    UtilBlock.init(plugin);
    UtilMobs.init(plugin);
    UtilItem.init(plugin);

    InventoryAPI.init(plugin);

    this.displayCompiler = new ItemDisplayCompiler(plugin);
    plugin.getProtocolManager().addPacketListener(this.displayCompiler);
    this.hologramManager = new HologramManager(plugin);
    this.playerSkinManager = new PlayerSkinManager();
    this.actionBarManager = new ActionBarManager(plugin);
    this.infoBarManager = new InfoBarManager(plugin, (entity) -> new InfoBar(entity, this.infoBarManager));
    this.protocolAPI = new ProtocolAPI(plugin);
    this.npcAPI = new NpcAPI(plugin);
    final EmptyTablist et = new EmptyTablist(this.tabListManager);
    this.tabListManager = new TabListManager(plugin, (p) -> et);
  }

}
