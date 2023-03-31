package cl.bgm.achievements.discord;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit extends PlayerListener implements DiscordConstants {
  private DiscordManager discordManager;

  public PlayerQuit(DiscordManager discordManager) {
    this.discordManager = discordManager;
  }

  public void onPlayerQuit(PlayerQuitEvent e) {
    Player player = e.getPlayer();
    DiscordWebhook webhook = discordManager.getDiscordWebhook();
    webhook.addEmbed(
        new DiscordWebhook.EmbedObject()
            .setDescription(
                "Player has left! ("
                    + (Bukkit.getOnlinePlayers().length - 1)
                    + "/"
                    + Bukkit.getMaxPlayers()
                    + ")")
            .setAuthor(
                player.getName(),
                DiscordManager.NAMEMC_URL + player.getName(),
                DiscordManager.MC_HEADS_URL + player.getName())
            .setColor(RED));
    try {
      webhook.execute();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
