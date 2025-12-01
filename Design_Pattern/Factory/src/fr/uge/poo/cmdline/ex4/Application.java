package fr.uge.poo.cmdline.ex4;


import java.util.List;

public class Application {

  public static void main (String[] args) {
    var settingsBuilder = ApplicationSettings.builder();
    String[] arguments = {
        "-legacy", "-with-borders", "-window-name", "Factory",
        "-border-with", "5", "filename1", "--version", "1", "2",
        "-h", "Selym", "OUMAKHLOUF", "anotherFile"
    };

    var cmdParser = new CmdLineParser();

    // No parameter
    cmdParser.addFlag("-legacy", () -> settingsBuilder.legacy(true));
    cmdParser.addFlag("-with-borders", () -> settingsBuilder.bordered(true));
    cmdParser.addOption(Option.builder()
            .name("-with-borders")
            .action(_ -> settingsBuilder.bordered(true))
            .doc("-with-borders : Enables the drawing of borders")
           .build());

    // One parameter
    cmdParser.addOption(Option.builder()
            .name("-window-name")
            .nbArguments(1)
            .action(params -> settingsBuilder.windowName(params.getFirst()))
            .doc("-window-name : Sets the name of the window")
            .build());
    cmdParser.addOptionWithOneParameter("-border-with", width -> settingsBuilder.borderWith(Integer.parseInt(width)));

    // Multiple parameters
    cmdParser.addOption(Option.builder()
        .name("-v")
        .alias("--version")
        .required(true)
        .nbArguments(2)
        .action(params -> System.out.println("Version : " + params.getFirst() + "." + params.getLast()))
        .doc("-v, --version : Prints the version of the application")
        .build()
    );

    cmdParser.addOption(Option.builder()
            .name("-h")
            .aliases(List.of("--help", "--doc"))
            .required(false)
            .nbArguments(2)
            .action(params -> System.out.println("Help for " + params.getFirst() + " " + params.getLast()))
        .build()
    );


    List<String> result = cmdParser.process(arguments);
    var settings = settingsBuilder.build();
    System.out.println(settings);

    cmdParser.usage();

  }

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
}