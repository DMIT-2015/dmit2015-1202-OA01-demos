package dmit2015.model;

/**
 * This class models a Circle shape.
 *
 * @author Sam Wu
 * @version 2021.01.14
 *
 */
public class Circle {
    // Declare data fields to store data
    private double radius;

    // Create getters/setters to encapsulate access to the data fields
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        // If the new radius is greater than zero
        if (radius > 0) {
            this.radius = radius;
        } else {
            // Throw an unchecked exception
            throw new RuntimeException("The radius must be greater than zero.");
        }
    }

    // Construct a Circle with a raduis of 1
    public Circle() {
        radius = 1; //
    }

    // Construct a Circle with a specified radius
    public Circle(double radius) {
        this.radius = radius;
    }

    /**
     * Calculate the area of the circle using the formula: area = PI * radius * radius
     * @return The calculated area of the circle
     */
    public double area() {
        return Math.PI * radius * radius;
    }

    /**
     * Calculate the diameter of the circle using the formula: diameter = 2 * radius
     * @return The calculated diameter of the circle
     */
    public double diameter() {
        return 2 * radius;
    }

    /**
     * Calculate the circumference of the circle using the formula: circumference = 2 * PI * radius
     * @return The calculated circumference of the circle
     */
    public double circumference() {
        return 2 * Math.PI * radius;
    }

}
