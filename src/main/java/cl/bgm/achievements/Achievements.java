package cl.bgm.achievements;

import cl.bgm.achievements.command.CoordinatesCommand;
import cl.bgm.achievements.command.StatisticsCommand;
import cl.bgm.achievements.db.SQLiteConnector;
import cl.bgm.achievements.discord.DiscordConstants;
import cl.bgm.achievements.discord.DiscordManager;
import cl.bgm.achievements.stats.StatsModelManager;
import cl.bgm.achievements.wool.WoolMonument;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Achievements extends JavaPlugin implements DiscordConstants {
  private static final String SQLITE_FILE = "db.db";
  private static final String CONFIG_FILE = "config.yml";

  private static Achievements achievements;

  private SQLiteConnector connector;
  private Timer timer;
  private WoolMonument woolMonument;
  private StatsModelManager statsManager;
  private DiscordManager discordManager;
  private AchievementsConfig config;

  public static Achievements get() {
    return achievements;
  }

  @Override
  public void onEnable() {
    achievements = this;

    // Load Configuration
    this.createDataFolder();
    this.createDefaultConfiguration(CONFIG_FILE);
    this.loadConfig();

    // Init Timer
    this.timer = new Timer();

    // Connect Database
    this.connector = new SQLiteConnector(new File(getDataFolder() + "/" + SQLITE_FILE).getPath());

    // Init Objective Managers
    this.woolMonument = new WoolMonument();
    this.statsManager = new StatsModelManager(this.connector, this.timer);
    this.discordManager = new DiscordManager(this.config);

    // Register Commands
    this.registerCommands();

    // Logging
    Bukkit.getLogger().info(String.format("%s enabled.", getDescription().getFullName()));
  }

  @Override
  public void onDisable() {
    // Logging
    this.connector.disconnect();
  }

  public void registerEvent(Event.Type type, Listener listener, Event.Priority priority) {
    Bukkit.getServer().getPluginManager().registerEvent(type, listener, priority, this);
  }

  private void createDataFolder() {
    if (getDataFolder().exists()) return;
    if (getDataFolder().mkdir()) return;

    Bukkit.getLogger()
        .severe(
            String.format("Could not create data folder for %s", getDescription().getFullName()));
  }

  private void loadConfig() {
    getConfiguration().load();

    try {
      this.config = new AchievementsConfig(getConfiguration(), getDataFolder());
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create a default configuration file from the .jar.
   *
   * @param name Config file name
   */
  private void createDefaultConfiguration(String name) {
    File actual = new File(getDataFolder(), name);
    if (!actual.exists()) {
      InputStream input = null;
      try {
        JarFile file = new JarFile(getFile());
        ZipEntry copy = file.getEntry(name);
        if (copy == null) throw new FileNotFoundException();
        input = file.getInputStream(copy);
      } catch (IOException e) {
        Bukkit.getLogger()
            .severe(getDescription().getName() + ": Unable to read default configuration: " + name);
      }
      if (input != null) {
        FileOutputStream output = null;

        try {
          output = new FileOutputStream(actual);
          byte[] buf = new byte[8192];
          int length = 0;
          while ((length = input.read(buf)) > 0) {
            output.write(buf, 0, length);
          }

          Bukkit.getLogger()
              .info(getDescription().getName() + ": Default configuration file written: " + name);
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            if (input != null) input.close();
          } catch (IOException ignored) {
          }

          try {
            if (output != null) output.close();
          } catch (IOException ignored) {
          }
        }
      }
    }
  }

  private void registerCommands() {
    this.getCommand(CoordinatesCommand.NAME).setExecutor(new CoordinatesCommand());
    this.getCommand(StatisticsCommand.NAME).setExecutor(new StatisticsCommand(this.statsManager));
  }
}
