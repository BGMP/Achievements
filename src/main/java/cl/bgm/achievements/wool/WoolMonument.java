package cl.bgm.achievements.wool;

import cl.bgm.achievements.Achievements;
import cl.bgm.achievements.AchievementsConfig;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
      new LinkedHashMap<DyeColor, ChatColor>() {
        {
          put(DyeColor.WHITE, ChatColor.WHITE);
          put(DyeColor.ORANGE, ChatColor.GOLD);
          put(DyeColor.MAGENTA, ChatColor.LIGHT_PURPLE);
          put(DyeColor.LIGHT_BLUE, ChatColor.BLUE);
          put(DyeColor.YELLOW, ChatColor.YELLOW);
          put(DyeColor.LIME, ChatColor.GREEN);
          put(DyeColor.PINK, ChatColor.LIGHT_PURPLE);
          put(DyeColor.GRAY, ChatColor.DARK_GRAY);
          put(DyeColor.SILVER, ChatColor.GRAY);
          put(DyeColor.CYAN, ChatColor.DARK_AQUA);
          put(DyeColor.PURPLE, ChatColor.DARK_PURPLE);
          put(DyeColor.BLUE, ChatColor.DARK_BLUE);
          put(DyeColor.BROWN, ChatColor.DARK_RED);
          put(DyeColor.GREEN, ChatColor.DARK_GREEN);
          put(DyeColor.RED, ChatColor.RED);
          put(DyeColor.BLACK, ChatColor.BLACK);
        }
      };

  private AchievementsConfig config;
  private World world = Bukkit.getWorlds().get(0);
  private HashMap<Wool, Location> wools = new HashMap<>();

  public WoolMonument(AchievementsConfig config) {
    this.config = config;
    this.setWoolPlacements();

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

  public static String getWoolPlacedMsg(Player player, Block block) {
    return String.format(
        WOOL_PLACED_MSG,
        player.getDisplayName(),
        getPrettyWoolName(DyeColor.getByData(block.getData())),
        ChatColor.WHITE);
  }

  private static String getPrettyWoolName(DyeColor color) {
    String[] name = color.name().toLowerCase().split("_"); // get name parts
    String cap = name[0].substring(0, 1).toUpperCase() + name[0].substring(1); // capitalise
    if (name.length > 1) {
      cap =
          String.format("%s %s", cap, name[1].substring(0, 1).toUpperCase() + name[1].substring(1));
    }

    return colorMap.get(color)
        + cap; // final, coloured wool name ("magenta_wool" => "Magenta Wool")
  }

  private void setWoolPlacements() {
    Location origin =
        new Location(
            this.world,
            this.config.getWoolMonumentOriginX(),
            this.config.getWoolMonumentOriginY() - 1,
            this.config.getWoolMonumentOriginZ());
    BlockFace orientation = this.config.getWoolMonumentOrientation();
    Location offsetVector =
        new Location(
            this.world, orientation.getModX(), orientation.getModY(), orientation.getModZ());

    for (DyeColor color : colorMap.keySet()) {
      this.wools.put(new Wool(color), origin.clone());
      origin.add(offsetVector);
    }
  }

  private boolean isSameLocation(Location l1, Location l2) {
    return l1.getBlockX() == l2.getBlockX()
        && l1.getBlockY() == l2.getBlockY()
        && l1.getBlockZ() == l2.getBlockZ();
  }
}
