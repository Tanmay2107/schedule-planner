package view;

import java.util.Objects;

public class Rectangle {
  private double x1;
  private double y1;
  private double x2;
  private double y2;

  public Rectangle(double x1, double y1, double width, double height) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x1 + width;
    this.y2 = y1 + height;
  }

  public boolean contains(double x, double y) {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof Rectangle) {
      Rectangle that = (Rectangle) o;
      return this.x1 == that.x1 && this.y1 == that.y1 && this.x2 == that.x2 && this.y2 == that.y2;
    }
    else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x1, this.y1, this.x2, this.y2);
  }


}
