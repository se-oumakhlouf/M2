package fr.uge.logger;

import java.io.IOException;

public sealed interface Logger permits PathLogger, SystemLogger{

  enum LogLevel {
    INFO, WARNING, ERROR
  }

  void log(LogLevel level, String message) throws IOException;

}
