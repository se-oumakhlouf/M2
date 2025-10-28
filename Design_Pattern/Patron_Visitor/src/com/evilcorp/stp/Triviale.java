package com.evilcorp.stp;

import java.util.Optional;
import java.util.Scanner;

public class Triviale {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter commands:");
    try  {
      while (scanner.hasNextLine()) {
        String nextLine = scanner.nextLine();
        if (nextLine.equals("quit")) {
          break;
        }

        Optional<STPCommand> optCommand = STPParser.parse(nextLine);
        if (optCommand.isEmpty()) {
          System.out.println("Pas compris");
          continue;
        }
        if (optCommand.get() instanceof HelloCmd) {
          System.out.println("Au revoir");
        } else {
          System.out.println("non implémenté");
        }

      }
    } finally {
      scanner.close();
    }
  }

}
