package cl.bgm.achievements.wool;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class BlockBreak extends BlockListener {
  private WoolMonument monument;

  public BlockBreak(WoolMonument monument) {
    this.monument = monument;
  }

  public void onBlockBreak(BlockBreakEvent e) {
    Block block = e.getBlock();
    if (block == null) return;

    Material material = block.getType();
    if (material != Material.WOOL) return;
    if (!this.monument.checkWoolPlaced(block)) return;

    Player player = e.getPlayer();
    player.sendMessage(ChatColor.RED + "You may not break the monument's wools!");
    e.setCancelled(true);
  }
}
