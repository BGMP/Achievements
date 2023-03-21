package cl.bgm.achievements.stats;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit extends PlayerListener {
  private StatsModelManager statsModelManager;

  public PlayerQuit(StatsModelManager statsModelManager) {
    this.statsModelManager = statsModelManager;
  }

  public void onPlayerQuit(PlayerQuitEvent e) {
    this.statsModelManager.unloadPlayer(e.getPlayer());
  }
}
