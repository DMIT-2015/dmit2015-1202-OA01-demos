package dmit2015.view;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SeleniumJupiter.class)
class CirclePageTest {

    @Test
    void testWithFireFox(FirefoxDriver driver) {
        driver.get("http://localhost:8080/dmit2015-1202-oa01-instructor-demos/circle.jsp");
        assertThat(driver.getTitle(), containsString("Circle Calculator"));
        WebElement radiusElement = driver.findElement(By.id("radius"));
        radiusElement.clear();
        radiusElement.sendKeys("1");
        WebElement submitElement = driver.findElement(By.id("submit"));
        submitElement.click();
        WebElement areaElement = driver.findElement(By.id("area"));
        assertThat(areaElement.getText(), containsString("Area = 3.141592653589793"));
    }

}