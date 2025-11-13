package fr.uge.poo.composite.filesystem.v2;

import fr.uge.poo.composite.filesystem.v2.FileSystemItem.DirectoryEager;
import fr.uge.poo.composite.filesystem.v2.FileSystemItem.File;
import fr.uge.poo.composite.filesystem.v2.FileSystemItem.LazyDirectoryProxy;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Application {

  public static void main(String[] args) {

    Path path = Path.of("./src");
    String extension = "java";
    System.out.println("Files from path=" + path + " with extension=java :");
    System.out.println(Application.findFilesWithExtension(path, extension));

  }

  private static List<String> findFilesWithExtension(Path path, String extension) {
    Objects.requireNonNull(path);
    Objects.requireNonNull(extension);

    FileSystemItem fileSystemItem = FileSystemItem.of(path).orElse(null);
    if (fileSystemItem == null) {
      return List.of();
    }

    List<String> filesWithExtension = new ArrayList<>();
    collectFilesWithExtension(fileSystemItem, extension, filesWithExtension);

    return filesWithExtension;
  }

  private static void collectFilesWithExtension(FileSystemItem item, String extension, List<String> result) {

    switch (item) {
      case File file :
        if (file.extension().equals(extension)) {
          result.add(file.name());
        }
        break;

      case Directory directory :
        directory.content().stream().filter(Optional::isPresent).map(Optional::get).forEach(child -> collectFilesWithExtension(child, extension, result));
        break;
    }
  }

}
