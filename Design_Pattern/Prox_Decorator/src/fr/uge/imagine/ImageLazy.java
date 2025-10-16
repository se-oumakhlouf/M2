package fr.uge.imagine;

import java.util.Objects;
import java.util.logging.Logger;

public class ImageLazy implements Image {

  private Image image;
  private final String url;

  public ImageLazy (String url) {
    this.url = Objects.requireNonNull(url);
  }

  private Image downloader () {
    if (image == null) {
      return Image.download(url);
    }
    return image;
  }

  @Override
  public String name () {
    return downloader().name ();
  }

  @Override
  public int size () {
    return downloader().size ();
  }

  @Override
  public double hue () {
    return downloader().hue();
  }
}
