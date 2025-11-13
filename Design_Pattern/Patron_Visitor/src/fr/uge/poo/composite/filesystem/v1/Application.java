package fr.uge.poo.composite.filesystem.v1;

import fr.uge.poo.composite.filesystem.printer.FileSystemPrinter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application {

  public static void main(String[] args) {

    Path path = Path.of("./src");
    FileSystem fileSystem = FileSystem.of(path);
    System.out.println(FileSystemPrinter.treeString(fileSystem));

    String extension = "java";
    System.out.println("Files from path=" + path + " with extension=java :");
    System.out.println(Application.findFilesWithExtension(path, extension));

    FileSystem lazyFileSystem = FileSystem.ofLazy(path);
    System.out.println("\nLazy FileSystem from path=" + path + " :");
    System.out.println(FileSystemPrinter.treeString(lazyFileSystem));
  }

  private static List<String> findFilesWithExtension(Path directory, String extension) {
    Objects.requireNonNull(directory);
    Objects.requireNonNull(extension);

    FileSystem item = FileSystem.of(directory);
    ArrayList<String> files = new ArrayList<>();

    collectFilesWithExtension(item, extension, files);

    return files;
  }

  private static void collectFilesWithExtension(FileSystem item, String extension, List<String> files) {
    if (item instanceof Folder folder) {
      for (var dir : folder.contents()) {
        collectFilesWithExtension(dir, extension, files);
      }
    } else if (item instanceof File file) {
      if (file.extension().equals(extension)) {
        files.add(file.name());
      }
    }
  }

}
