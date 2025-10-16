package fr.uge.imagine;

public class ImageRaw implements Image {

  private final String name;
  private final int size;
  private final double hue;

  public ImageRaw(String name, int size, double hue) {
    this.name = name;
    this.size = size;
    this.hue = hue;
  }

  @Override
  public String name () {
    return name;
  }

  @Override
  public int size () {
    return size;
  }

  @Override
  public double hue () {
    return hue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Image)) return false;

    Image image = (Image) o;

    if (size != image.size()) return false;
    if (Double.compare(image.hue(), hue) != 0) return false;
    return name.equals(image.name());
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = name.hashCode();
    result = 31 * result + size;
    temp = Double.doubleToLongBits(hue);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

}
