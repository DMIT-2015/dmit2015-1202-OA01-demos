package dmit2015.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    @Test
    void area() {
        // Create a Circle with a radius of 1
        Circle circle1 = new Circle();
        // The radius should be 1
        assertEquals(1, circle1.getRadius());
        // The area should be 3.14
        assertEquals(Math.PI, circle1.area(), 0);
    }

    @Test
    void diameter() {
        // Create a Circle with a radius of 5
        Circle circle1 = new Circle(5);
        // The radius should be 5
        assertEquals(5, circle1.getRadius());
        // The diameter should be 10
        assertEquals(10, circle1.diameter(), 0);
    }

    @Test
    void circumference() {
        // Create a Circle with a default radius
        Circle circle1 = new Circle();
        // Change the radius to 10
        circle1.setRadius(10);
        // The radius should be 10
        assertEquals(10, circle1.getRadius());
        // The circumference should be 62.83
        assertEquals(62.83, circle1.circumference(), 0.005);
    }

    @Test
    void shouldThrowException() {
        // Create a Circle with a default radius
        Circle circle1 = new Circle();
        // A exception should be thrown when the radius is set to an invalid value
        RuntimeException exception = assertThrows(
             RuntimeException.class,
                () -> circle1.setRadius(-10)
        );
        // The exception message should be "The radius must be greater than zero."
        assertEquals("The radius must be greater than zero.", exception.getMessage());
    }
}