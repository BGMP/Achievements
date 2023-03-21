package cl.bgm.achievements.db.model;

import java.util.UUID;

public class PlayerStats {
  public static String TABLE = "player_stats";

  public static String UUID_COL = "uuid";
  public static String NAME_COL = "name";
  public static String BLOCKS_MINED_COL = "blocks_mined";
  public static String BLOCKS_PLACED_COL = "blocks_placed";

  private UUID uuid;
  private String name;
  private int blocksMined;
  private int blocksPlaced;

  public PlayerStats(UUID uuid, String name, int blocksMined, int blocksPlaced) {
    this.uuid = uuid;
    this.name = name;
    this.blocksMined = blocksMined;
    this.blocksPlaced = blocksPlaced;
  }

  public UUID getUUID() {
    return uuid;
  }

  public String getName() {
    return name;
  }

  public int getBlocksMined() {
    return blocksMined;
  }

  public int getBlocksPlaced() {
    return blocksPlaced;
  }

  public void addBlockMined() {
    this.blocksMined++;
  }

  public void addBlockPlaced() {
    this.blocksPlaced++;
  }
}
