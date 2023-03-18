package cl.bgm.achievements;

import cl.bgm.achievements.wool.WoolMonument;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class Achievements extends JavaPlugin {
  private static Achievements achievements;
  private static PluginDescriptionFile descriptionFile;

  public static Achievements get() {
    return achievements;
  }

  public static PluginDescriptionFile getDesc() {
    return descriptionFile;
  }

  @Override
  public void onEnable() {
    achievements = this;
    descriptionFile = achievements.getDescription();

    // Init WoolMonument
    WoolMonument monument = new WoolMonument();

    Bukkit.getLogger().info(String.format("%s enabled.", getDesc().getFullName()));
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }

  public void registerEvent(Event.Type type, Listener listener, Event.Priority priority) {
    Bukkit.getServer().getPluginManager().registerEvent(type, listener, priority, this);
  }
}
