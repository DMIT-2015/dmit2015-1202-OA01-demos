package dmit2015.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void area() {
        // Create a rectangle with a length of 10 and width of 20
        Rectangle rectangle1 = new Rectangle(10,20);
        // The length should be 10
        assertEquals(10, rectangle1.getLength());
        // The width should be 20
        assertEquals(20, rectangle1.getWidth());
        // The area should 200
        assertEquals(200, rectangle1.area());
    }

    @Test
    void perimeter() {
        // Create a rectangle
        Rectangle rectangle1 = new Rectangle();
        // Set the length to 30
        rectangle1.setLength(30);
        // The length should be 30
        assertEquals(30, rectangle1.getLength());
        // Set the width to 50
        rectangle1.setWidth(50);
        // The width should be 50
        assertEquals(50, rectangle1.getWidth());
        // The perimeter should be 160
        assertEquals(160, rectangle1.perimeter());
    }

    @Test
    void shouldThrowException() {
        // Create a rectangle object
        Rectangle rectangle1 = new Rectangle();
        // An exception should be thrown when the length is assign a negative number
        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> rectangle1.setLength(-10)
        );
        assertEquals("Length must be greater than zero.", ex.getMessage());
    }

    @Test
    void shouldCreateImageFile() throws IOException {
        // Create rectangle of 640 x 480
        Rectangle rectangle1 = new Rectangle(640, 480);
        File rectangleFile = rectangle1.createImage();
        assertNotNull(rectangleFile);
//        Files.write(Paths.get("/tmp/rectangle1.png"), new FileInputStream(rectangleFile).readAllBytes());
    }
}