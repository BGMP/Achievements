package cl.bgm.achievements.wool;

import cl.bgm.achievements.Achievements;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.material.Wool;

/**
 * Represents a wool monument for each coloured {@link Wool}. The monument origins from 0 0 and
 * expands towards the positive Z axis by default.
 */
public class WoolMonument {
  private static final String WOOL_PLACED_MSG = "%s placed %s %swool!";
  private static final Map<DyeColor, ChatColor> colorMap =
      new HashMap<DyeColor, ChatColor>() {
        {
          put(DyeColor.BLACK, ChatColor.BLACK);
          put(DyeColor.BLUE, ChatColor.DARK_BLUE);
          put(DyeColor.GREEN, ChatColor.DARK_GREEN);
          put(DyeColor.CYAN, ChatColor.DARK_AQUA);
          put(DyeColor.RED, ChatColor.RED);
          put(DyeColor.PURPLE, ChatColor.DARK_PURPLE);
          put(DyeColor.ORANGE, ChatColor.GOLD);
          put(DyeColor.SILVER, ChatColor.GRAY);
          put(DyeColor.GRAY, ChatColor.DARK_GRAY);
          put(DyeColor.LIGHT_BLUE, ChatColor.BLUE);
          put(DyeColor.LIME, ChatColor.GREEN);
          put(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE);
          put(DyeColor.YELLOW, ChatColor.YELLOW);
          put(DyeColor.BROWN, ChatColor.DARK_RED);
          put(DyeColor.PINK, ChatColor.LIGHT_PURPLE);
          put(DyeColor.WHITE, ChatColor.WHITE);
        }
      };
  private static final double Y = 72.0;

  private World world = Bukkit.getWorlds().get(0);
  private Map<Wool, Location> wools = new HashMap<>();

  public WoolMonument() {
    this.wools.put(new Wool(DyeColor.WHITE), new Location(this.world, 0, Y, 0));
    this.wools.put(new Wool(DyeColor.ORANGE), new Location(this.world, 0, Y, 1));
    this.wools.put(new Wool(DyeColor.MAGENTA), new Location(this.world, 0, Y, 2));
    this.wools.put(new Wool(DyeColor.LIGHT_BLUE), new Location(this.world, 0, Y, 3));
    this.wools.put(new Wool(DyeColor.YELLOW), new Location(this.world, 0, Y, 4));
    this.wools.put(new Wool(DyeColor.LIME), new Location(this.world, 0, Y, 5));
    this.wools.put(new Wool(DyeColor.PINK), new Location(this.world, 0, Y, 6));
    this.wools.put(new Wool(DyeColor.GRAY), new Location(this.world, 0, Y, 7));
    this.wools.put(new Wool(DyeColor.SILVER), new Location(this.world, 0, Y, 8));
    this.wools.put(new Wool(DyeColor.CYAN), new Location(this.world, 0, Y, 9));
    this.wools.put(new Wool(DyeColor.PURPLE), new Location(this.world, 0, Y, 10));
    this.wools.put(new Wool(DyeColor.BLUE), new Location(this.world, 0, Y, 11));
    this.wools.put(new Wool(DyeColor.BROWN), new Location(this.world, 0, Y, 12));
    this.wools.put(new Wool(DyeColor.GREEN), new Location(this.world, 0, Y, 13));
    this.wools.put(new Wool(DyeColor.RED), new Location(this.world, 0, Y, 14));
    this.wools.put(new Wool(DyeColor.BLACK), new Location(this.world, 0, Y, 15));

    Achievements.get()
        .registerEvent(Event.Type.BLOCK_PLACE, new BlockPlace(this), Event.Priority.Normal);
    Achievements.get()
        .registerEvent(Event.Type.BLOCK_BREAK, new BlockBreak(this), Event.Priority.Normal);
  }

  public boolean checkWoolPlaced(Block block) {
    assert block.getType() == Material.WOOL;

    for (Wool woolObj : wools.keySet()) {
      Location location = this.wools.get(woolObj);
      if (!isSameLocation(block.getLocation(), location)) continue;

      return block.getData() == woolObj.getColor().getData();
    }

    return false;
  }

  public static String getCompletionMessage(Player player, Block block) {
    return String.format(
        WOOL_PLACED_MSG,
        player.getDisplayName(),
        getPrettyWoolName(DyeColor.getByData(block.getData())),
        ChatColor.WHITE);
  }

  private static String getPrettyWoolName(DyeColor color) {
    String[] name = colorMap.get(color).name().toLowerCase().split("_"); // get name parts
    String cap = name[0].substring(0, 1).toUpperCase() + name[0].substring(1); // capitalise
    if (name.length > 1) {
      cap =
          String.format(
              "%s",
              String.format(
                  "%s %s", cap, name[1].substring(0, 1).toUpperCase() + name[1].substring(1)));
    }

    return colorMap.get(color)
        + cap; // final, coloured wool name ("magenta_wool" => "Magenta Wool")
  }

  private boolean isSameLocation(Location l1, Location l2) {
    return l1.getBlockX() == l2.getBlockX()
        && l1.getBlockY() == l2.getBlockY()
        && l1.getBlockZ() == l2.getBlockZ();
  }
}
