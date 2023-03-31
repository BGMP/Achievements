package cl.bgm.achievements;

import java.io.File;
import org.bukkit.util.config.Configuration;

public class AchievementsConfig implements Config {
  private Configuration config;
  private File datafolder;

  private String discordWebhookURL;

  public AchievementsConfig(Configuration config, File datafolder) {
    this.config = config;
    this.datafolder = datafolder;

    this.discordWebhookURL = config.getString("discord-webhook-url");
  }

  @Override
  public String getDiscordWebhookURL() {
    return discordWebhookURL;
  }
}
