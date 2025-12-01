package fr.uge.poo.cmdline.ex4;

import java.util.*;
import java.util.function.Consumer;

public class Option {

  private final String name;
  private final int nbArguments;
  private final Consumer<Iterator<String>> action;
  private final boolean required;
  private final List<String> aliases;
  private final String doc;

  private Option (String name, int nbArgs, Consumer<Iterator<String>> action, List<String> aliases, boolean required,
   String doc) {
    this.name = name;
    this.nbArguments = nbArgs;
    this.action = action;
    this.required = required;
    this.aliases = Collections.unmodifiableList(aliases);
    this.doc = doc;
  }

  public static OptionBuilder builder () {
    return new OptionBuilder();
  }

  public void accept (Iterator<String> arguments) {
    action.accept(arguments);
  }

  public boolean isRequired () {
    return required;
  }

  public String name () {
    return name;
  }

  public List<String> aliases () {
    return aliases;
  }

  public String doc() {
		return doc;
  }

  public static class OptionBuilder {
    private String name;
    private int nbArgs = 0;
    private Consumer<Iterator<String>> action;
    private boolean required = false;
    private final List<String> aliases = new ArrayList<>();
    private String doc = "";

    public OptionBuilder name (String name) {
      this.name = Objects.requireNonNull(name);
      return this;
    }

    public OptionBuilder nbArguments (int nbArgs) {
      this.nbArgs = nbArgs;
      return this;
    }

    public OptionBuilder action (Consumer<List<String>> action) {
      Objects.requireNonNull(action);

      this.action = iterator -> {
        List<String> parameters = new ArrayList<>();
        for (int i = 0; i < nbArgs; i++) {
          if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Option " + name + " requires " + nbArgs + " argument(s), but only "
                                               + i + " provided");
          }
          String param = iterator.next();
          if (param.startsWith("-")) {
            throw new IllegalArgumentException("Option " + name + " expected an argument " +
                                               "but" + " found option " + param);
          }
          parameters.add(param);
        }
        action.accept(parameters);
      };

      return this;
    }

    public OptionBuilder required (boolean required) {
      this.required = required;
      return this;
    }

    public OptionBuilder alias (String alias) {
      this.aliases.add(Objects.requireNonNull(alias));
      return this;
    }

    public OptionBuilder aliases (List<String> aliases) {
      this.aliases.addAll(Objects.requireNonNull(aliases));
      return this;
    }

    public OptionBuilder doc (String doc) {
      this.doc = Objects.requireNonNull(doc);
      return this;
    }

    public Option build () {
      return new Option(name, nbArgs, action, aliases, required, doc);
    }
  }

}
