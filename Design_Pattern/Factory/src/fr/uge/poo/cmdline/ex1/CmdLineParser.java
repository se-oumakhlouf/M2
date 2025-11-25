package fr.uge.poo.cmdline.ex1;

import java.util.*;

public class CmdLineParser {

  private final Map<String, Runnable> registeredOptions = new HashMap<>();
  private final Set<String> optionsSeen = new HashSet<>();

  public void registerOption(String option, Runnable runnable) {
    Objects.requireNonNull(option);
    Objects.requireNonNull(runnable);
    registeredOptions.put(option, runnable);
  }

  public Set<String> getOptionsSeen() {
    return Collections.unmodifiableSet(optionsSeen);
  }

  public List<String> process(String[] arguments) {
    ArrayList<String> files = new ArrayList<>();
    for (String argument : arguments) {
      if (registeredOptions.containsKey(argument)) {
        optionsSeen.add(argument);
        registeredOptions.get(argument).run();
      } else {
        files.add(argument);
      }
    }
    return files;
  }

}