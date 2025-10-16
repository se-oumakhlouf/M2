package fr.uge.imagine;

import java.util.Map;

public class Main {

  public static void main (String[] args) {
    var map = Map.of("cat", "http://www.example.com/cat.png",
        "dog", "http://www.example.com/dog.png",
        "mice", "http://www.example.com/mice.png");
//        var images =map.values().stream().map(Image::download).toList();
//        System.out.println(images.get(0).hue());

    System.out.println("-----------\n\t Lazy");
    var lazyImages = map.values().stream().map(Image::downloadLazy).toList();
    System.out.println("Normalement rien est téléchargé pour le moment");
    System.out.println(lazyImages.get(0).hue());
    System.out.println(lazyImages.get(1).hue());
  }
}
