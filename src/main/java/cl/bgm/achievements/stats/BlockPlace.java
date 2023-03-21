package cl.bgm.achievements.stats;

import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends BlockListener {
  private StatsModelManager statsModelManager;

  public BlockPlace(StatsModelManager statsModelManager) {
    this.statsModelManager = statsModelManager;
  }

  public void onBlockPlace(BlockPlaceEvent e) {
    this.statsModelManager.addPlacedBlock(e.getPlayer());
  }
}
