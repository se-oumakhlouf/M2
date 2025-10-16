package fr.uge.logger;


final class SystemLogger implements Logger{

  public void log(LogLevel level, String message) {
    System.err.println(level + " " + message);
  }
}