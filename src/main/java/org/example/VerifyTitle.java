package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class VerifyTitle {
    public static void main(String[] args) {

        // setup incongnito chorme mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // driver wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.get("https://www.saucedemo.com/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //expected
        String expectedTitle = "Products";

        //actual
        WebElement headerElement  = driver.findElement(By.xpath("//span[@class='title']"));
        String actualTitle = headerElement.getText();

        System.out.println("text trên web là : " + actualTitle);

        //compare
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("✅ TEST PASSED: Tiêu đề đúng như mong đợi!");
        }
        else {
            System.out.println("❌ TEST FAILED: Sai rồi!");
            System.out.println("mong đợi : " + expectedTitle);
            System.out.println("thực tế : " + actualTitle);
        }

        // close
        driver.quit();
    }
}
