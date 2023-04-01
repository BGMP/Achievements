package cl.bgm.achievements;

import org.bukkit.block.BlockFace;

public interface Config {

  // Discord Integration
  String getDiscordWebhookURL();

  // Wool Monument
  BlockFace getWoolMonumentOrientation();

  float getWoolMonumentOriginX();

  float getWoolMonumentOriginY();

  float getWoolMonumentOriginZ();
}
