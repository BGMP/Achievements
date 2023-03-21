package cl.bgm.achievements.command;

import cl.bgm.achievements.db.model.PlayerStats;
import cl.bgm.achievements.stats.StatsModelManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatisticsCommand implements CommandExecutor {
  public static final String NAME = "statistics";

  private StatsModelManager statsModelManager;

  public StatisticsCommand(StatsModelManager statsModelManager) {
    this.statsModelManager = statsModelManager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!command.getName().equalsIgnoreCase(NAME)) return true;

    if (args.length > 1) {
      sender.sendMessage("Incorrect usage. Correct usage: /stats [name]");
      return true;
    }

    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to execute this command.");
      return true;
    }

    if (args.length == 0) {
      Player player = (Player) sender;
      PlayerStats stats = this.statsModelManager.getPlayerStats(player);

      sender.sendMessage(
          ChatColor.RED
              + "---------------- "
              + player.getDisplayName()
              + ChatColor.RED
              + " ----------------");
      sender.sendMessage(
          ChatColor.GOLD + "Blocks Placed: " + ChatColor.AQUA + stats.getBlocksPlaced());
      sender.sendMessage(
          ChatColor.GOLD + "Blocks Mined: " + ChatColor.AQUA + stats.getBlocksMined());

      return true;
    }

    String nick = args[0];
    Player player = Bukkit.getPlayer(nick);
    if (player == null) {
      sender.sendMessage(ChatColor.RED + "No player matched query.");
      return true;
    }

    PlayerStats stats = this.statsModelManager.getPlayerStats(player);
    sender.sendMessage(
        ChatColor.RED
            + "---------------- "
            + (player.isOnline() ? player.getDisplayName() : ChatColor.DARK_AQUA + player.getName())
            + ChatColor.RED
            + " ----------------");
    sender.sendMessage(
        ChatColor.GOLD + "Blocks Placed: " + ChatColor.AQUA + stats.getBlocksPlaced());
    sender.sendMessage(ChatColor.GOLD + "Blocks Mined: " + ChatColor.AQUA + stats.getBlocksMined());

    return true;
  }
}
