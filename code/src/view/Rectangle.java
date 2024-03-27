package view;

import java.util.Objects;

/**
 * Represents a rectangle defined by its top-left corner coordinates
 * (x1, y1) and its bottom-right corner coordinates (x2, y2).
 */
public class Rectangle {
  private double x1;
  private double y1;
  private double x2;
  private double y2;

  /**
   * Constructs a rectangle with the specified top-left corner coordinates (x1, y1),
   * width, and height.
   * @param x1 The x-coordinate of the top-left corner.
   * @param y1 The y-coordinate of the top-left corner.
   * @param width The width of the rectangle.
   * @param height The height of the rectangle.
   */
  public Rectangle(double x1, double y1, double width, double height) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x1 + width;
    this.y2 = y1 + height;
  }

  /**
   * Checks if the given point (x, y) is contained within this rectangle.
   * @param x The x-coordinate of the point.
   * @param y The y-coordinate of the point.
   * @return true if the point is inside the rectangle, false otherwise.
   */
  public boolean contains(double x, double y) {
    return x >= x1 && x <= x2 && y >= y1 && y <= y2;
  }


  /**
   * Compares this rectangle to the specified object for equality.
   * Two rectangles are considered equal if their corresponding corner coordinates are equal.
   * @param o The object to compare with.
   * @return true if the rectangles are equal, false otherwise.
   */
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

  /**
   * Computes a hash code for this rectangle.
   * The hash code is computed based on the corner coordinates.
   * @return The hash code value for this rectangle.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x1, this.y1, this.x2, this.y2);
  }


}
