package cl.bgm.achievements.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoordinatesCommand implements CommandExecutor {
  public static final String NAME = "coordinates";

  // §NOTE: Line breaks don't seem to work properly... might be related to legacy issues.
  private static final String COORDS_MSG = "%s:\n\n%s\n\n%s\n\n%s";

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!command.getName().equalsIgnoreCase(NAME)) return false;
    if (!(sender instanceof Player)) {
      sender.sendMessage("You must be a player to execute this command.");
      return false;
    }

    Player player = (Player) sender;
    Location location = player.getLocation();

    player.sendMessage(player.getDisplayName() + ":");
    player.sendMessage(
        ChatColor.YELLOW + "X" + ChatColor.WHITE + ": " + ChatColor.GRAY + location.getBlockX());
    player.sendMessage(
        ChatColor.YELLOW + "Y" + ChatColor.WHITE + ": " + ChatColor.GRAY + location.getBlockY());
    player.sendMessage(
        ChatColor.YELLOW + "Z" + ChatColor.WHITE + ": " + ChatColor.GRAY + location.getBlockZ());

    return true;
  }
}
