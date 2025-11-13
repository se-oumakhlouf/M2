package fr.uge.poo.composite.filesystem.v1;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Folder implements FileSystem {

  private final String name;
  private final Path path;
  private final List<FileSystem> content;

  public Folder(Path path, String name) {
    this.name = Objects.requireNonNull(name);
    this.path = Objects.requireNonNull(path);
    this.content = new ArrayList<>();
  }

  @Override
  public String name () {
    return name;
  }

  @Override
  public Path path () {
    return path;
  }

  public void add (FileSystem item) {
    this.content.add(Objects.requireNonNull(item));
  }

  public List<FileSystem> contents() {
    return List.copyOf(content);
  }

  @Override
  public String toString() {
    String items = content.stream().map(FileSystem::toString).collect(Collectors.joining(", ", "\t", "\n"));
    StringBuilder builder = new StringBuilder();

    builder
        .append("name=").append(name).append("\n")
        .append("path=").append(path).append("\n")
        .append("content={\n").append(items).append("}");

    return builder.toString();
  }
}
