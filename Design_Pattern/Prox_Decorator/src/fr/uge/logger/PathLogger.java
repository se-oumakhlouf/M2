package fr.uge.logger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.rmi.UnexpectedException;
import java.util.Objects;

final class PathLogger implements Closeable, Logger {

  private final BufferedWriter fileWriter;
  private LogLevel minLevel = LogLevel.INFO;

  public PathLogger(Path path) throws IOException {
    Objects.requireNonNull(path);
    this.fileWriter = Files.newBufferedWriter(path,
        StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }

  public void setMinLevel(LogLevel minLevel) {
    this.minLevel = Objects.requireNonNull(minLevel);
  }

  public void log(LogLevel level, String message) throws IOException {
    Objects.requireNonNull(message);
    Objects.requireNonNull(level);

    if (level.ordinal() <= minLevel.ordinal()) {
      try {
        fileWriter.write(level + " " + message);
        fileWriter.newLine();
        fileWriter.flush();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  @Override
  public void close () throws IOException {
    this.fileWriter.close();
  }
}
