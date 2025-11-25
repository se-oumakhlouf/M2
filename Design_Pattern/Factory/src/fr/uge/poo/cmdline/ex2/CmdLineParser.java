package fr.uge.poo.cmdline.ex2;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {


  private final Set<String> optionsSeen = new HashSet<>();

  private final Map<String, Consumer<Iterator<String>>> registeredOptions = new HashMap<>();

  public void registerOption (String option, Runnable runnable) {
    Objects.requireNonNull(option);
    Objects.requireNonNull(runnable);
    registeredOptions.put(option, _ -> runnable.run());
  }

  public void addOptionWithOneParameter (String option, Consumer<String> consumer) {
    Objects.requireNonNull(option);
    Objects.requireNonNull(consumer);

    registeredOptions.put(option, stringIterator -> {
      if (!stringIterator.hasNext()) {
        throw new IllegalArgumentException("Missing parameter for option " + option);
      }
      consumer.accept(stringIterator.next());
    });
  }

  public Set<String> getOptionsSeen () {
    return Collections.unmodifiableSet(optionsSeen);
  }

  public List<String> process(String[] arguments) {
    ArrayList<String> files = new ArrayList<>();
    Iterator<String> iterator = Arrays.asList(arguments).iterator();

    while (iterator.hasNext()) {
      String argument = iterator.next();
      Consumer<Iterator<String>> optionConsumer = registeredOptions.get(argument);
      if (optionConsumer != null) {
        optionsSeen.add(argument);
        optionConsumer.accept(iterator);
      } else {
        files.add(argument);
      }
    }
    return files;
  }

}