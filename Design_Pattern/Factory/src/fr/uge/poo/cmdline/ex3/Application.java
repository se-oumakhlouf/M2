package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.List;

public class Application {

  // The record ApplicationSettings is used to store the settings of the application,
  // does the application start with a border, does it uses the legacy drawing engine.
  public record ApplicationSettings(boolean legacy, boolean bordered, int borderWith, String windowName) {

    public static Builder builder () {
      return new Builder();
    }

    static public class Builder {

      private boolean legacy = false;
      private boolean bordered = true;
      private int borderWith = 1;
      private String windowName = "MyWindow";

      public Builder legacy (boolean legacy) {
        this.legacy = legacy;
        return this;
      }

      public Builder bordered (boolean bordered) {
        this.bordered = bordered;
        return this;
      }

      public Builder borderWith (int borderWith) {
        this.borderWith = borderWith;
        return this;
      }

      public Builder windowName (String windowName) {
        this.windowName = windowName;
        return this;
      }

      public ApplicationSettings build () {
        return new ApplicationSettings(legacy, bordered, borderWith, windowName);
      }
    }

  }


  public static void main (String[] args) {
    var settingsBuilder = ApplicationSettings.builder();
    String[] arguments = {"-legacy", "-with-borders", "-window-name", "Factory", "-border-with", "5", "filename1",
        "filename2", "fileName", "-v", "1", "209", "quelque chose"};

    var cmdParser = new CmdLineParser();

    // No parameter
    cmdParser.addFlag("-legacy", () -> settingsBuilder.legacy(true));
    cmdParser.addFlag("-with-borders", () -> settingsBuilder.bordered(true));
    cmdParser.addFlag("-no-borders", () -> settingsBuilder.bordered(false));

    // One parameter
    cmdParser.addOptionWithOneParameter("-window-name", settingsBuilder::windowName);
    cmdParser.addOptionWithOneParameter("-border-with", width -> settingsBuilder.borderWith(Integer.parseInt(width)));

    // Multiple parameters
    cmdParser.addOption(Option.builder()
            .name("-v")
            .nbArguments(2)
            .action(params -> System.out.println("Version : " + params.getFirst() + "." + params.getLast()))
            .build()
    );


    List<String> result = cmdParser.process(arguments);
    var seen = cmdParser.getOptionsSeen();

    var settings = settingsBuilder.build();
    System.out.println(seen);
    System.out.println(settings);

  }
}