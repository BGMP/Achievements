package cl.bgm.achievements;

import java.io.File;
import org.bukkit.block.BlockFace;
import org.bukkit.util.config.Configuration;

public class AchievementsConfig implements Config {
  private Configuration config;
  private File datafolder;

  private String discordWebhookURL;
  private float woolMonumentOriginX;
  private float woolMonumentOriginY;
  private float woolMonumentOriginZ;
  private BlockFace woolMonumentOrientation;

  public AchievementsConfig(Configuration config, File datafolder) {
    this.config = config;
    this.datafolder = datafolder;

    this.discordWebhookURL = config.getString("discord-webhook-url");
    this.woolMonumentOriginX = (float) config.getDouble("wool-monument.origin.x", 0);
    this.woolMonumentOriginY = (float) config.getDouble("wool-monument.origin.y", 0);
    this.woolMonumentOriginZ = (float) config.getDouble("wool-monument.origin.z", 0);
    this.woolMonumentOrientation = BlockFace.valueOf(config.getString("wool-monument.orientation"));
  }

  @Override
  public String getDiscordWebhookURL() {
    return discordWebhookURL;
  }

  @Override
  public BlockFace getWoolMonumentOrientation() {
    return woolMonumentOrientation;
  }

  @Override
  public float getWoolMonumentOriginX() {
    return woolMonumentOriginX;
  }

  @Override
  public float getWoolMonumentOriginY() {
    return woolMonumentOriginY;
  }

  @Override
  public float getWoolMonumentOriginZ() {
    return woolMonumentOriginZ;
  }
}
