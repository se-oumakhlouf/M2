package fr.uge.poo.composite.filesystem.v1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

public sealed interface FileSystem permits File, Folder, FolderLazy {

  String name ();

  Path path ();

  static FileSystem of (Path path) {
    return Files.isDirectory(path) ? createFolder(path) : createFile(path);
  }

  static FileSystem ofLazy (Path path) {
    return Files.isDirectory(path) ? createFolder(path, true) : createFile(path);
  }

  private static FileSystem createFile (Path path) {
    Objects.requireNonNull(path);
    String fileName = path.getFileName().toString();
    String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
    return new File(path.getParent(), fileName, extension);
  }

  private static FileSystem createFolder (Path path, boolean isLazy) {
    Objects.requireNonNull(path);
    String folderName = path.getFileName().toString();

    if (isLazy) {
      return new FolderLazy(path, folderName);
    }

    Folder folder = new Folder(path, folderName);

    try (Stream<Path> stream = Files.list(path)) {
      stream.forEach(subPath -> folder.add(of(subPath)));
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
    return folder;
  }

  private static FileSystem createFolder (Path path) {
    return createFolder(path, false);
  }

}
