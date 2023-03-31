package cl.bgm.achievements.discord;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PlayerJoin extends PlayerListener implements DiscordConstants {
  private DiscordManager discordManager;

  public PlayerJoin(DiscordManager discordManager) {
    this.discordManager = discordManager;
  }

  public void onPlayerJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    DiscordWebhook webhook = discordManager.getDiscordWebhook();
    webhook.addEmbed(
        new DiscordWebhook.EmbedObject()
            .setDescription(
                "Player has joined! ("
                    + Bukkit.getOnlinePlayers().length
                    + "/"
                    + Bukkit.getMaxPlayers()
                    + ")")
            .setAuthor(
                player.getName(),
                DiscordManager.NAMEMC_URL + player.getName(),
                DiscordManager.MC_HEADS_URL + player.getName())
            .setColor(GREEN));
    try {
      webhook.execute();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
