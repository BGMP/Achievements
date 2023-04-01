package cl.bgm.achievements.discord;

import cl.bgm.achievements.stats.Milestone;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public interface DiscordConstants {

  // Colours
  Color RED = new Color(0.5F, 0.0F, 0.0F);
  Color GREEN = new Color(0.0F, 0.5F, 0.0F);
  Color BLUE = new Color(0.5F, 0.0F, 0.0F);
  Color PURPLE = new Color(138, 43, 226);

  // Milestones
  Set<Milestone<Integer>> BLOCKS_PLACED_MILESTONES =
      new HashSet<Milestone<Integer>>() {
        {
          add(new Milestone<>(1000, "%p has placed 1000 blocks!"));
          add(new Milestone<>(5000, "%p has placed 5000 blocks!"));
          add(new Milestone<>(10000, "%p has placed 10000 blocks!"));
          add(new Milestone<>(100000, "%p has placed 100000 blocks!"));
        }
      };

  Set<Milestone<Integer>> BLOCKS_MINED_MILESTONES =
      new HashSet<Milestone<Integer>>() {
        {
          add(new Milestone<>(1000, "%p has mined 1000 blocks!"));
          add(new Milestone<>(5000, "%p has mined 5000 blocks!"));
          add(new Milestone<>(10000, "%p has mined 10000 blocks!"));
          add(new Milestone<>(100000, "%p has mined 100000 blocks!"));
        }
      };
}
