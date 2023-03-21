package cl.bgm.achievements.stats;

import cl.bgm.achievements.Achievements;
import cl.bgm.achievements.db.SQLiteConnector;
import cl.bgm.achievements.db.model.PlayerStats;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class StatsModelManager extends TimerTask {
  private SQLiteConnector connector;
  private Timer timer;

  private Set<PlayerStats> playerStats = new HashSet<>();

  public StatsModelManager(SQLiteConnector connector, Timer timer) {
    this.connector = connector;
    this.timer = timer;

    // Create associated table if absent
    this.createSQLTable();

    Achievements.get()
        .registerEvent(Event.Type.BLOCK_PLACE, new BlockPlace(this), Event.Priority.Monitor);
    Achievements.get()
        .registerEvent(Event.Type.BLOCK_BREAK, new BlockBreak(this), Event.Priority.Monitor);
    Achievements.get()
        .registerEvent(Event.Type.PLAYER_JOIN, new PlayerJoin(this), Event.Priority.Monitor);
    Achievements.get()
        .registerEvent(Event.Type.PLAYER_QUIT, new PlayerQuit(this), Event.Priority.Monitor);

    this.timer.schedule(this, 150L * 1000L, 300L * 1000L);
  }

  public void deployPlayerStats() {
    this.playerStats.forEach(this::deployPlayerStats);
  }

  public void unloadPlayer(Player player) {
    PlayerStats playerStats = this.getPlayerStats(player);
    if (playerStats == null) return;

    this.deployPlayerStats(playerStats);
    this.playerStats.remove(playerStats);
  }

  public void addMinedBlock(Player player) {
    PlayerStats stats = this.getPlayerStats(player);
    if (stats != null) stats.addBlockMined();
  }

  public void addPlacedBlock(Player player) {
    PlayerStats stats = this.getPlayerStats(player);
    if (stats != null) stats.addBlockPlaced();
  }

  public PlayerStats getPlayerStats(Player player) {
    return this.playerStats.stream()
        .filter(ps -> ps.getUUID().equals(player.getUniqueId()))
        .findFirst()
        .orElse(null);
  }

  public void loadPlayer(Player player) {
    this.connector.connect();

    try {
      PreparedStatement statement =
          this.connector
              .getConnection()
              .prepareStatement("SELECT * FROM " + PlayerStats.TABLE + " WHERE uuid = ?");
      statement.setString(1, player.getUniqueId().toString());

      ResultSet results = statement.executeQuery();
      if (!results.next()) {
        this.registerPlayer(player);
        return;
      }

      this.playerStats.add(
          new PlayerStats(
              player.getUniqueId(),
              results.getString(PlayerStats.NAME_COL),
              results.getInt(PlayerStats.BLOCKS_MINED_COL),
              results.getInt(PlayerStats.BLOCKS_PLACED_COL)));
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.connector.disconnect();
    }
  }

  private void registerPlayer(Player player) {
    this.connector.connect();

    try {
      PreparedStatement statement =
          this.connector
              .getConnection()
              .prepareStatement(
                  "INSERT INTO "
                      + PlayerStats.TABLE
                      + " (uuid, name, blocks_mined, blocks_placed) VALUES (?, ?, 0, 0)");
      statement.setString(1, player.getUniqueId().toString());
      statement.setString(2, player.getName());
      statement.executeUpdate();

      this.playerStats.add(new PlayerStats(player.getUniqueId(), player.getName(), 0, 0));
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.connector.disconnect();
    }
  }

  private void createSQLTable() {
    this.connector.connect();

    try {
      final Statement statement = this.connector.getConnection().createStatement();
      statement.executeUpdate(
          "CREATE TABLE IF NOT EXISTS "
              + PlayerStats.TABLE
              + "(uuid VARCHAR PRIMARY KEY, name VARCHAR, blocks_placed VARCHAR, blocks_mined VARCHAR);");
      Bukkit.getLogger().info("Initialised PlayerStats table: " + PlayerStats.TABLE);
    } catch (SQLException e) {
      Bukkit.getLogger().severe("Error initialising PlayerStats's table: " + PlayerStats.TABLE);
      e.printStackTrace();
    }

    this.connector.disconnect();
  }

  private void deployPlayerStats(PlayerStats playerStats) {
    this.connector.connect();

    try {
      PreparedStatement statement =
          this.connector
              .getConnection()
              .prepareStatement(
                  "UPDATE "
                      + PlayerStats.TABLE
                      + " SET name = ?, blocks_mined = ?, blocks_placed = ? WHERE uuid = ?");
      statement.setString(1, playerStats.getName());
      statement.setInt(2, playerStats.getBlocksMined());
      statement.setInt(3, playerStats.getBlocksPlaced());
      statement.setString(4, playerStats.getUUID().toString());
      statement.executeUpdate();
    } catch (SQLException exception) {
      exception.printStackTrace();
    } finally {
      this.connector.disconnect();
    }
  }

  @Override
  public void run() {
    deployPlayerStats();
  }
}
