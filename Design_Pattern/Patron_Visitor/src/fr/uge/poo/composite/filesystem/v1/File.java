package fr.uge.poo.composite.filesystem.v1;

import java.nio.file.Path;
import java.util.Objects;

public record File(Path path, String name, String extension) implements FileSystem {

  public File {
    Objects.requireNonNull(name);
    Objects.requireNonNull(path);
    Objects.requireNonNull(extension);
  }

  @Override
  public String toString() {
    return "File{" +
      "name='" + name + '\'' +
      ", path=" + path +
      ", extension='" + extension + '\'' +
      '}';
  }

}
