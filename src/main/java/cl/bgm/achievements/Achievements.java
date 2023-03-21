package cl.bgm.achievements;

import cl.bgm.achievements.command.CoordinatesCommand;
import cl.bgm.achievements.command.StatisticsCommand;
import cl.bgm.achievements.db.SQLiteConnector;
import cl.bgm.achievements.stats.StatsModelManager;
import cl.bgm.achievements.wool.WoolMonument;
import java.io.File;
import java.util.Timer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Achievements extends JavaPlugin {
  private static final String SQLITE_FILE = "db.db";

  private static Achievements achievements;

  private SQLiteConnector connector;
  private Timer timer;
  private WoolMonument woolMonument;
  private StatsModelManager statsManager;

  public static Achievements get() {
    return achievements;
  }

  @Override
  public void onEnable() {
    achievements = this;
    // Init Timer
    this.timer = new Timer();

    // Connect Database
    this.createDataFolder();
    this.connector = new SQLiteConnector(new File(getDataFolder() + "/" + SQLITE_FILE).getPath());

    // Init Objective Managers
    this.woolMonument = new WoolMonument();
    this.statsManager = new StatsModelManager(this.connector, this.timer);

    // Register Commands
    this.registerCommands();

    Bukkit.getLogger().info(String.format("%s enabled.", getDescription().getFullName()));
  }

  @Override
  public void onDisable() {
    this.connector.disconnect();
  }

  private void createDataFolder() {
    if (getDataFolder().exists()) return;
    if (getDataFolder().mkdir()) return;

    Bukkit.getLogger()
        .severe(
            String.format("Could not create data folder for %s", getDescription().getFullName()));
  }

  private void registerCommands() {
    this.getCommand(CoordinatesCommand.NAME).setExecutor(new CoordinatesCommand());
    this.getCommand(StatisticsCommand.NAME).setExecutor(new StatisticsCommand(this.statsManager));
  }

  public void registerEvent(Event.Type type, Listener listener, Event.Priority priority) {
    Bukkit.getServer().getPluginManager().registerEvent(type, listener, priority, this);
  }
}
