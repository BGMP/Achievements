package cl.bgm.achievements.stats;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerJoin extends PlayerListener {
  private StatsModelManager statsModelManager;

  public PlayerJoin(StatsModelManager statsModelManager) {
    this.statsModelManager = statsModelManager;
  }

  public void onPlayerJoin(PlayerJoinEvent e) {
    this.statsModelManager.loadPlayer(e.getPlayer());
  }
}
