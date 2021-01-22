package dmit2015.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class models a Rectangle shape.
 *
 * @author Sam Wu
 * @version 2021.01.15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rectangle {

    /** The length of the rectangle */
    private double length = 1;

    /** The width of the rectangle */
    private double width = 1;

    public void setLength(double length) {
        if (length > 0) {
            this.length = length;
        } else {
            throw new RuntimeException("Length must be greater than zero.");
        }
    }

    public void setWidth(double width) {
        if (width > 0) {
            this.width = width;
        } else {
            throw new RuntimeException("Width must be greater than zero.");
        }
    }

    /**
     * Calculate the area of the rectangle
     *
     * @return the area of the rectangle
     */
    public double area() {
        return length * width;
    }

    /**
     * Calculate the perimeter of the rectangle
     *
     *
     * @return the perimeter of the rectangle
     */
    public double perimeter() {
        return 2 * (length + width);
    }

    public File createImage() throws IOException {
        int imageWidth = (int) width;
        int imageHeight = (int) length;
        BufferedImage rectangleImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rectangleImage.createGraphics();
        g2d.setStroke(new BasicStroke(20.0f));
//        g2d.setColor(Color.pink);
//        g2d.fillRect(0,0, imageWidth, imageHeight);
        g2d.setColor(Color.green);
        g2d.draw(new Rectangle2D.Double(10,10, imageWidth - 20, imageHeight - 20));
        g2d.dispose();

        File imageFile = new File("/home/user2015/Pictures/rectangle0.png");
        ImageIO.write(rectangleImage, "png", imageFile);

        return imageFile;
    }

}
