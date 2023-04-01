package cl.bgm.achievements.stats;

public class Milestone<T> {
  private T value;
  private String message;

  public Milestone(T value, String message) {
    this.value = value;
    this.message = message;
  }

  public T getValue() {
    return value;
  }

  public String getMessage() {
    return message;
  }
}
