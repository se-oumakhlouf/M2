package fr.uge.logger;

import java.util.Objects;

final class SystemLogger implements Logger{

  private static final SystemLogger INSTANCE = new SystemLogger();
  private LogLevel minLevel = LogLevel.INFO;

  private SystemLogger(){}

  public static SystemLogger getInstance() {
    return INSTANCE;
  }

  public void setMinLevel(LogLevel minLevel) {
    this.minLevel = Objects.requireNonNull(minLevel);
  }

  @Override
  public void log(LogLevel level, String message) {
    Objects.requireNonNull(message);
    Objects.requireNonNull(level);
    if (level.ordinal() <= minLevel.ordinal()) {
      System.err.println(level + " " + message);
    }
  }
}