package fr.uge.logger;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class FullLogger implements Logger, Closeable {

  private final List<Logger> loggers;

  public FullLogger() {
    this.loggers = new ArrayList<>();
  }

  public void addLogger(Logger logger) {
    this.loggers.add(Objects.requireNonNull(logger));
  }

  @Override
  public void log (LogLevel level, String message) throws IOException {
    Objects.requireNonNull(message);
    Objects.requireNonNull(level);

    for (Logger logger : loggers) {
      logger.log(level, message);
    }
  }

  @Override
  public void close () throws IOException {
    for (Logger logger : loggers) {
      if (logger instanceof Closeable closeable) {
        closeable.close();
      }
    }
  }
}
