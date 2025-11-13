package fr.uge.poo.composite.filesystem.v2;

import fr.uge.poo.composite.filesystem.v2.FileSystemItem.DirectoryEager;
import fr.uge.poo.composite.filesystem.v2.FileSystemItem.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public sealed interface FileSystemItem permits Directory, File {

  Path path ();

  String name ();

  static Optional<FileSystemItem> of (Path path) {

    if (Files.notExists(path)) {
      return Optional.empty();
    }

    if (Files.isRegularFile(path)) {
      return Optional.of(new File(path, path.getFileName().toString(), path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf('.') + 1)));
    }
    if (Files.isDirectory(path)) {
      try (Stream<Path> stream = Files.list(path)) {
        return Optional.of(new DirectoryEager(path, path.getFileName().toString(), stream.map(FileSystemItem::of).toList()));
      } catch (IOException ioe) {
        return Optional.empty();
      }
    }

    return Optional.empty();
  }

  static Optional<FileSystemItem> ofLazy (Path path) {
    if (Files.notExists(path)) {
      return Optional.empty();
    }

    if (Files.isRegularFile(path)) {
      return Optional.of(new File(path, path.getFileName().toString(),
          path.getFileName().toString().substring(path.getFileName().toString().lastIndexOf('.') + 1)));
    }

    if (Files.isDirectory(path)) {
      return Optional.of(new DirectoryLazy(path, path.getFileName().toString()));
    }

    return Optional.empty();
  }

  record File(Path path, String name, String extension) implements FileSystemItem {
    public File {
      Objects.requireNonNull(name);
      Objects.requireNonNull(path);
    }
  }

}
