package cl.bgm.achievements.stats;

import cl.bgm.achievements.db.model.PlayerStats;
import cl.bgm.achievements.discord.DiscordConstants;
import cl.bgm.achievements.discord.DiscordManager;
import cl.bgm.achievements.discord.DiscordWebhook;
import java.io.IOException;
import java.util.Optional;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends BlockListener implements DiscordConstants {
  private StatsModelManager statsModelManager;
  private DiscordManager discordManager;

  public BlockPlace(StatsModelManager statsModelManager, DiscordManager discordManager) {
    this.statsModelManager = statsModelManager;
    this.discordManager = discordManager;
  }

  public void onBlockPlace(BlockPlaceEvent e) {
    PlayerStats stats = this.statsModelManager.addPlacedBlock(e.getPlayer());
    if (stats == null) return;

    Optional<Milestone<Integer>> milestone =
        BLOCKS_PLACED_MILESTONES.stream()
            .filter(m -> m.getValue() == stats.getBlocksPlaced())
            .findFirst();
    if (!milestone.isPresent()) return;

    Player player = e.getPlayer();
    String congrats = milestone.get().getMessage().replaceAll("%p", player.getDisplayName());

    player.sendMessage(ChatColor.GREEN + congrats);

    DiscordWebhook webhook = discordManager.getDiscordWebhook();
    webhook.addEmbed(
        new DiscordWebhook.EmbedObject()
            .setDescription(milestone.get().getMessage().replaceAll("%p", player.getName()))
            .setAuthor(
                player.getName(),
                DiscordManager.NAMEMC_URL + player.getName(),
                DiscordManager.MC_HEADS_URL + player.getName())
            .setColor(PURPLE));
    try {
      webhook.execute();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
