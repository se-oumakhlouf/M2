package fr.uge.poo.cmdline.ex3;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {


  private final Set<String> optionsSeen = new HashSet<>();

  private final Map<String, Option> registeredOptions = new HashMap<>();

  public void addFlag (String name, Runnable runnable) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(runnable);

    Option option = Option.builder()
        .name(name)
        .action(params -> runnable.run())
        .build();
    registeredOptions.put(name, option);
  }

  public void addOptionWithOneParameter (String name, Consumer<String> action) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(action);

    Option option = Option.builder()
        .name(name)
        .nbArguments(1)
        .action(params -> action.accept(params.getFirst()))
        .build();

    registeredOptions.put(name, option);
  }

  public void addOption(Option option) {
    Objects.requireNonNull(option);
    registeredOptions.put(option.getName(), option);
  }

  public Set<String> getOptionsSeen () {
    return Collections.unmodifiableSet(optionsSeen);
  }

  public List<String> process(String[] arguments) {
    ArrayList<String> files = new ArrayList<>();
    Iterator<String> iterator = Arrays.asList(arguments).iterator();
    while (iterator.hasNext()) {
      String text = iterator.next();
      if (text.startsWith("-")) {
        Option option = registeredOptions.get(text);
        if (option == null) {
          throw new IllegalArgumentException("Unknown option " + text);
        }
        optionsSeen.add(text);
        option.accept(iterator);
      } else {
        files.add(text);
      }
    }

    for (Map.Entry<String, Option> entry : registeredOptions.entrySet()) {
      if (entry.getValue().isRequired() && !optionsSeen.contains(entry.getKey())) {
        throw new IllegalArgumentException("Missing required option " + entry.getKey());
      }
    }

    return files;
  }

}