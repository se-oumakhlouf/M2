package fr.uge.logger;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

final class PathLogger implements Closeable, Logger {

  private final BufferedWriter fileWriter;

  public PathLogger(Path path) throws IOException {
    Objects.requireNonNull(path);
    this.fileWriter = Files.newBufferedWriter(path,
        StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }

  public void log(LogLevel level, String message) throws IOException {
    fileWriter.write(message);
  }

  @Override
  public void close () throws IOException {
    this.fileWriter.close();
  }
}
