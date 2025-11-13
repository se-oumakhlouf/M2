package fr.uge.poo.composite.filesystem.v2;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

final class DirectoryLazy implements Directory {
  private final Path path;
  private final String name;
  private Directory item = null;

  DirectoryLazy (Path path, String name) {
    this.path = Objects.requireNonNull(path);
    this.name = Objects.requireNonNull(name);
  }

  private Directory loader() {
    if (item == null) {
      item = FileSystemItem.ofLazy(path).map(DirectoryEager.class::cast).orElse(null);
    }
    return item;
  }

  @Override
  public Path path() {
    return loader().path();
  }

  @Override
  public String name () {
    return loader().name();
  }

  public List<Optional<FileSystemItem>> content() {
    return loader().content();
  }
}