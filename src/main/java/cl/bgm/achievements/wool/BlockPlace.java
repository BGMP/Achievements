package cl.bgm.achievements.wool;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends BlockListener {
  private WoolMonument monument;

  public BlockPlace(WoolMonument monument) {
    this.monument = monument;
  }

  public void onBlockPlace(BlockPlaceEvent e) {
    Block block = e.getBlock();
    if (block == null) return;

    Material material = block.getType();
    if (material != Material.WOOL) return;
    if (!this.monument.checkWoolPlaced(block)) return;

    for (Player p : Bukkit.getOnlinePlayers()) {
      p.playEffect(p.getLocation(), Effect.CLICK1, 5);
    }

    Bukkit.broadcastMessage(WoolMonument.getCompletionMessage(e.getPlayer(), block));
  }
}
