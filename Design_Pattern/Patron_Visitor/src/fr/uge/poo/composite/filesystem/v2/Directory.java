package fr.uge.poo.composite.filesystem.v2;

import fr.uge.poo.composite.filesystem.v2.Directory.DirectoryEager;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public sealed interface Directory extends FileSystemItem permits DirectoryEager, DirectoryLazy {

  List<Optional<FileSystemItem>> content ();


  record DirectoryEager(Path path, String name, List<Optional<FileSystemItem>> content) implements Directory {
    public DirectoryEager {
      Objects.requireNonNull(name);
      Objects.requireNonNull(path);
      Objects.requireNonNull(content);
    }
  }
}
