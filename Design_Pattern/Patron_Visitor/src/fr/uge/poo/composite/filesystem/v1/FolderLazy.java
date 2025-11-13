package fr.uge.poo.composite.filesystem.v1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public final class FolderLazy implements FileSystem {

  private final Path path;
  private final String folderName;
  private Folder folder;

  public FolderLazy (Path path, String folderName) {
    this.path = Objects.requireNonNull(path);
    this.folderName = Objects.requireNonNull(folderName);
  }

  private Folder loader () {
    if (folder == null) {
      folder = new Folder(path, folderName);

      try (Stream<Path> stream = Files.list(path)) {
        stream.forEach(subPath -> folder.add(FileSystem.of(subPath)));
      } catch (IOException ioe) {
        throw new UncheckedIOException(ioe);
      }
    }
    return folder;
  }

  @Override
  public String name () {
    return loader().name();
  }

  @Override
  public Path path () {
    return loader().path();
  }

  @Override
  public String toString() {
    return loader().toString();
  }
}
