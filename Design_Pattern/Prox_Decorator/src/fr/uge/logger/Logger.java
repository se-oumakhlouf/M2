package fr.uge.logger;

import java.io.IOException;

public sealed interface Logger permits FullLogger, PathLogger, SystemLogger {

  enum LogLevel {
    ERROR, WARNING, INFO
  }

  void log(LogLevel level, String message) throws IOException;

}
