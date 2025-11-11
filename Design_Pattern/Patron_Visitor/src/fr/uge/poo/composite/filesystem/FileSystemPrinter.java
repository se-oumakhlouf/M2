package fr.uge.poo.composite.filesystem;


// AI generated printer
public class FileSystemPrinter {

  public static String treeString(FileSystem item) {
    var builder = new StringBuilder();
    buildTree(item, "", true, builder);
    return builder.toString();
  }

  private static void buildTree(FileSystem item, String prefix, boolean isLast, StringBuilder builder) {
    builder.append(prefix);
    builder.append(isLast ? "└── " : "├── ");

    if (item instanceof File file) {
      builder.append("📄 ").append(file.name());
      if (!file.extension().isEmpty()) {
        builder.append(" (.").append(file.extension()).append(")");
      }
      builder.append("\n");

    } else if (item instanceof Folder folder) {
      builder.append("📁 ").append(folder.name()).append("/\n");

      var contents = folder.contents();
      for (int i = 0; i < contents.size(); i++) {
        boolean lastChild = (i == contents.size() - 1);
        String newPrefix = prefix + (isLast ? "    " : "│   ");
        buildTree(contents.get(i), newPrefix, lastChild, builder);
      }
    }
  }
}