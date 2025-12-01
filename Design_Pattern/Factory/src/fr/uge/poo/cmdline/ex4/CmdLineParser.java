package fr.uge.poo.cmdline.ex4;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    registeredOptions.put(option.name(), option);
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
        Option option = isAliasUsed(text);
        if (option == null) {
          throw new IllegalArgumentException("Unknown option " + text);
        }
        optionsSeen.add(text);
        option.accept(iterator);
      } else {
        files.add(text);
      }
    }
    areAllRequiredOptionsPresent();
    return files;
  }

  public void usage() {
    System.out.println(
    registeredOptions.values().stream()
        .filter(opt -> !opt.doc().isEmpty())
        .sorted(Comparator.comparing(Option::name))
        .map(Option::doc)
        .collect(Collectors.joining("\n|\t", "Usage:\n|\t", ""))
    );
  }

  private void areAllRequiredOptionsPresent() {
    for (Option option : registeredOptions.values()) {
      if (option.isRequired() && !sawOption(option)) {
        throw new IllegalArgumentException("Missing required option " + option.name());
      }
    }
  }

  private boolean sawOption(Option option) {
    return optionsSeen.contains(option.name()) || option.aliases().stream().anyMatch(optionsSeen::contains);
  }

  private Option isAliasUsed(String name) {
    return registeredOptions.values().stream().filter(option -> option.aliases().contains(name) || option.name().equals(name)).findFirst().orElse(null);
  }

}