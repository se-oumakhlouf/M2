package fr.uge.logger;

import fr.uge.logger.Logger.LogLevel;

import java.io.IOException;
import java.nio.file.Path;

public class Application {

  public static void main(String[] args) throws IOException {

    SystemLogger systemLogger = SystemLogger.getInstance();
    systemLogger.setMinLevel(LogLevel.INFO);

    PathLogger pathLogger = new PathLogger(Path.of("logs.txt"));
    pathLogger.setMinLevel(LogLevel.WARNING);

    try (FullLogger fullLogger = new FullLogger()) {
      fullLogger.addLogger(systemLogger);
      fullLogger.addLogger(pathLogger);

      fullLogger.log(LogLevel.INFO, "Hello World!");
      fullLogger.log(LogLevel.WARNING, "Hello World!");
      fullLogger.log(LogLevel.ERROR, "Hello World!");

      System.out.println("Hello World!");
    }
  }
}
