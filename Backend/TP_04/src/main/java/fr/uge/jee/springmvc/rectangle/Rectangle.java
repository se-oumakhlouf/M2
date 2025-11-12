package fr.uge.jee.springmvc.rectangle;

import jakarta.validation.constraints.Min;

public record Rectangle(
    @Min(value = 0, message = "height should be positive") int height,
    @Min(value = 0, message = "width should be positive") int width
) {

  public int area() {
    return height * width;
  }

}
