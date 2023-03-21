package cl.bgm.achievements.stats;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class BlockBreak extends BlockListener {
  private StatsModelManager statsModelManager;

  public BlockBreak(StatsModelManager statsModelManager) {
    this.statsModelManager = statsModelManager;
  }

  public void onBlockBreak(BlockBreakEvent e) {
    this.statsModelManager.addMinedBlock(e.getPlayer());
  }
}
