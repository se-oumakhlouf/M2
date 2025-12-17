package fr.uge.dragonball.locator.q2;

import java.util.List;
import java.util.Scanner;

// Design Pattern : Adapter
public class DragonBallFinder {
  public static void main(String[] args) throws InterruptedException {
    LocatorAPI locator = new BulmaLocatorAdapter();
    if (args.length > 0 && args[0].equals("-rm")) {
      locator = new RedArmyLocatorAdapter();
    }
    Scanner scanner = new Scanner(System.in);
    boolean quitter = false;

    while (!quitter) {
      System.out.println("\nBienvenue dans l'application Dragon Ball Finder !");
      System.out.println("Choisissez une option :");
      System.out.println("1. Localiser une Dragon Ball spÃ©cifique");
      System.out.println("2. Localiser toutes les Dragon Balls");
      System.out.println("3. Quitter");
      System.out.print("Votre choix : ");

      int choix = scanner.nextInt();
      switch (choix) {
        case 1:
          System.out.print("Entrez l'ID de la Dragon Ball (1-7) : ");
          int id = scanner.nextInt();
          if (id < 1 || id > 7) {
            System.out.println("ID invalide. Veuillez entrer un nombre entre 1 et 7.");
          } else {
            LocatorAPI.Point position = locator.locateAll().get(id - 1);
            if (position == null) {
              System.out.println("Dragon Ball " + id + " : Non localisÃ©e.");
            } else {
              System.out.println("Dragon Ball " + id + " : Latitude = " + position.x() + ", Longitude = " + position.y());
            }
          }
          break;
        case 2:
          System.out.println("Localisation de toutes les Dragon Balls...");
          List<LocatorAPI.Point> positions = locator.locateAll();
          for (int i = 0; i < positions.size(); i++) {
            LocatorAPI.Point pos = positions.get(i);
            if (pos == null) {
              System.out.println("Dragon Ball " + (i + 1) + " : Non localisÃ©e.");
            } else {
              System.out.println("Dragon Ball " + (i + 1) + " : Latitude = " + pos.x() + ", Longitude = " + pos.y());
            }
          }
          break;
        case 3:
          System.out.println("Merci d'avoir utilisÃ© Dragon Ball Finder. Au revoir !");
          quitter = true;
          break;
        default:
          System.out.println("Choix invalide. Veuillez choisir une option entre 1 et 3.");
          break;
      }
    }

    scanner.close();
  }
}