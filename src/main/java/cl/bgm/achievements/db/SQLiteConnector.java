package cl.bgm.achievements.db;

import cl.bgm.achievements.Achievements;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnector {
  private String url;
  private Connection connection;

  public SQLiteConnector(String filePath) {
    try {
      // This is required in Java 8
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    this.url = "jdbc:sqlite:" + filePath;
  }

  public Connection getConnection() {
    return connection;
  }

  public Connection connect() {
    synchronized (Achievements.class) {
      try {
        if (this.connection != null && !this.connection.isClosed()) {
          return null;
        }

        this.connection = DriverManager.getConnection(url);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return connection;
  }

  public void disconnect() {
    try {
      if (this.connection != null && !this.connection.isClosed()) {
        this.connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
