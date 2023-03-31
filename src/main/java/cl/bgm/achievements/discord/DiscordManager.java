package cl.bgm.achievements.discord;

import cl.bgm.achievements.Achievements;
import cl.bgm.achievements.AchievementsConfig;
import org.bukkit.event.Event;

public class DiscordManager {
  public static final String NAMEMC_URL = "https://namemc.com/";
  public static final String MC_HEADS_URL = "https://mc-heads.net/avatar/";

  private AchievementsConfig config;

  public DiscordManager(AchievementsConfig config) {
    this.config = config;

    Achievements.get()
        .registerEvent(Event.Type.PLAYER_JOIN, new PlayerJoin(this), Event.Priority.Monitor);
    Achievements.get()
        .registerEvent(Event.Type.PLAYER_QUIT, new PlayerQuit(this), Event.Priority.Monitor);
  }

  public DiscordWebhook getDiscordWebhook() {
    return new DiscordWebhook(this.config.getDiscordWebhookURL());
  }
}
